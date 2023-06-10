package xwm.osgi;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public class OsgiStart {

   public static void main(String[] args) {
      OsgiStart osgiStart = new OsgiStart();
      try {
         osgiStart.start();
      } catch (BundleException e) {
         throw new RuntimeException(e);
      }
   }

   public void start() throws BundleException {
      ServiceLoader<FrameworkFactory> ffs = ServiceLoader.load(FrameworkFactory.class);
      FrameworkFactory ff = ffs.iterator()
         .next();
      Map<String, String> config = new HashMap<String, String>();
      Framework fwk = ff.newFramework(config);
      fwk.start();

      BundleContext bc = fwk.getBundleContext();

      String eclipseVer = "1.0.0";
      String notVer = "1.0.0.SNAPSHOT";
      String eclipseBundle =
         "file:/Users/ultish/Developer/xwm/eclipse.bundle/target/eclipse.bundle-" + eclipseVer +
            "-SNAPSHOT" +
            ".jar";
      String notBundleBundle =
         "file:/Users/ultish/Developer/xwm/not.bundle/target/not.bundle-" + notVer + ".jar ";

      System.out.println(bc);

      Bundle[] bundles = bc.getBundles();
      boolean eclipseBundleInstalled = Stream.of(bundles)
         .anyMatch(x -> x.getSymbolicName()
            .equals("EclipseBundle") && x.getVersion()
            .toString()
            .startsWith(eclipseVer));

      boolean notBundleInstalled = Stream.of(bundles)
         .anyMatch(x -> x.getSymbolicName()
            .equals("not.bundle") && x.getVersion()
            .toString()
            .equals(notVer));

      if (!eclipseBundleInstalled) {
         System.out.println("Installing Eclipse Bundle...");
         bc.installBundle(eclipseBundle);
      }
      if (!notBundleInstalled) {
         System.out.println("Installing Not Bundle...");
         bc.installBundle(notBundleBundle);
      }

   }

}
