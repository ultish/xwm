package xwm.osgi;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

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
        FrameworkFactory ff = ffs.iterator().next();
        Map<String, String> config = new HashMap<String, String>();
        Framework fwk = ff.newFramework(config);
        fwk.start();

        BundleContext bc = fwk.getBundleContext();

        System.out.println(bc);

        Bundle bundle = bc.installBundle(
            "file:///Users/ultish/Developer/xwm/bundle-not-bundle/target/bundle-not-bundle-0.0.5-SNAPSHOT.jar");
        bundle.start();
        System.out.println(bundle.getBundleId());
    }

}
