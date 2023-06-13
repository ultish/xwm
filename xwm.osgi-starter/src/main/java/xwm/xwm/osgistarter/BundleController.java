package xwm.xwm.osgistarter;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bundles")
public class BundleController {

   @Autowired
   BundleService bundleService;

   @GetMapping
   public List<BundleWrapper> findAll() {
      return bundleService.findAll()
         .stream()
         .map(BundleWrapper::new)
         .collect(Collectors.toList());
   }

   @GetMapping(path = "pokemon")
   public List<BundleWrapper> findPokemon() {
      List<Bundle> bundles = bundleService.findAll()
         .stream()
         .collect(Collectors.toList());

      for (Bundle bundle : bundles) {
         bundle.getBundleId();

         try {
            // TODO so removing the Pokemon Interfaces from the Eclipse Bundle classpath is not
            //  working. It's not delegating to the bootloader CP even though it's been told it
            //  can delegate. Maybe we need to try Eclipse OSGI starter instead and see if that
            //  works? Not sure when EclipsePokemon is not assignable to PokemonType, and only
            //  reason I could think is the interface is a different class
            
            Class<?> aClass = bundle.loadClass("eclipse.bundle.EclipsePokemon");
            //            Class<?> aClass = bundle.loadClass("xwm.pokemon.PokemonType");

            if (aClass != null) {
               Field[] fields = aClass.getFields();
               System.out.println(fields);
               Object o = null;
               try {
                  o = aClass.newInstance();
                  //                  System.out.println("name: " + o.name());
                  System.out.println(o);
               } catch (InstantiationException e) {
                  throw new RuntimeException(e);
               }

            }
            // TODO we need to make a Pokemon extension point
            //  and use those plugins to call an interface.

            // TODO we need to load generic bundles that share code for other bundles
            //  we aren't loading the bundles into maven context to call directly from.
            //            System.out.println(aClass);

         } catch (ClassNotFoundException e) {
            System.out.println("No Class: " + e.getMessage());
         } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
         }
         //         BundleContext bundleContext = bundle.getBundleContext();
         //         System.out.println(bundleContext);
      }

      return bundles.stream()
         .map(BundleWrapper::new)
         .collect(Collectors.toList());

   }

   @GetMapping(path = "/install")
   @ResponseBody
   public BundleWrapper install(@RequestParam String file) throws BundleException {
      return new BundleWrapper(bundleService.installBundle(file));
   }

   @GetMapping(path = "/uninstall")
   @ResponseBody
   public boolean uninstall(@RequestParam long id) throws BundleException {
      return bundleService.uninstallBundle(id);
   }
}
