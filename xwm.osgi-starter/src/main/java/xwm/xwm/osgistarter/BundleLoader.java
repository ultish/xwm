package xwm.xwm.osgistarter;

import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BundleLoader {

   static BundleLoader instance = new BundleLoader();

   private final BundleContext bc;
   //   private final Framework fwk;

   private BundleLoader() {

      try {
         Map<String, String> config = new HashMap<String, String>();
         config.put("org.osgi.framework.bootdelegation", "*");
         config.put("osgi.parentClassloader", "fwk");
         config.put("osgi.contextClassLoaderParent", "fwk");

         config.put("osgi.bundles", "org.eclipse.equinox.common@2:start,org.eclipse.update" +
            ".configurator@3:start,org.eclipse.core.jobs@4:start,org.eclipse.core.runtime@start," +
            "org.eclipse.equinox.registy@start");
         config.put("osgi.clean", "true");
         config.put("eclipse.noRegistryCache", "true");

         EclipseStarter.setInitialProperties(config);
         bc = EclipseStarter.startup(new String[0], null);

         // TODO get Eclipse Platform for extension points

      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public static Bundle installBundle(String file) throws BundleException {
      if (!file.startsWith("file:")) {
         file = "file:" + file;
      }
      Bundle bundle = instance.bc.installBundle(file);

      return bundle;
   }

   public static boolean uninstallBundle(long id) throws BundleException {
      Optional<Bundle> first = Stream.of(instance.bc.getBundles())
         .filter(b -> b.getBundleId() == id)
         .findFirst();

      if (first.isPresent()) {
         first.get()
            .uninstall();
         return true;
      } else {
         return false;
      }
   }

   public static List<Bundle> getBundles() {
      return Stream.of(Optional.ofNullable(instance.bc.getBundles())
            .orElse(new Bundle[0]))
         .collect(Collectors.toList());
   }

}
