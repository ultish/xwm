package xwm.xwm.osgistarter;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
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
            Class<?> aClass = bundle.loadClass("eclipse.bundle.EclipsePokemon");

            if (aClass != null) {
               Field[] fields = aClass.getFields();
               System.out.println(fields);
               try {
                  Method name = aClass.getDeclaredMethod("name");

                  bundle.getBundleContext();
               } catch (NoSuchMethodException e) {
                  throw new RuntimeException(e);
               }

            }
            // TODO we need to make a Pokemon extension point
            //  and use those plugins to call an interface.

            // TODO we need to load generic bundles that share code for other bundles
            //  we aren't loading the bundles into maven context to call directly from.
            System.out.println(aClass);
            URL resource = bundle.getResource("eclipse/bundle/EclipsePokemon.class");
            if (resource != null) {

            }

         } catch (ClassNotFoundException e) {
            System.out.println("No Class: " + e.getMessage());
         }
         BundleContext bundleContext = bundle.getBundleContext();
         System.out.println(bundleContext);
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
