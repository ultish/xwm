package xwm.xwm.osgistarter;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BundleLoader {

   static BundleLoader instance = new BundleLoader();

   private final BundleContext bc;
   private final Framework fwk;

   private BundleLoader() {
      ServiceLoader<FrameworkFactory> ffs = ServiceLoader.load(FrameworkFactory.class);
      FrameworkFactory ff = ffs.iterator()
         .next();
      Map<String, String> config = new HashMap<String, String>();
      fwk = ff.newFramework(config);

      try {
         fwk.start();
         bc = fwk.getBundleContext();
      } catch (BundleException e) {
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
