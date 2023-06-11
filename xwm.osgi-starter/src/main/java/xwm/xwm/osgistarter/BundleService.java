package xwm.xwm.osgistarter;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BundleService {

   public List<Bundle> findAll() {
      return BundleLoader.getBundles();
   }

   public Bundle installBundle(String file) throws BundleException {
      return BundleLoader.installBundle(file);
   }

   public boolean uninstallBundle(long id) throws BundleException {
      return BundleLoader.uninstallBundle(id);
   }
}
