package xwm.xwm.osgistarter;

import org.osgi.framework.Bundle;

public class BundleWrapper {
   String version;
   String name;
   String location;

   public BundleWrapper(Bundle b) {
      if (b != null) {
         version = b.getVersion()
            .toString();
         name = b.getSymbolicName();
         location = b.getLocation();
      }
   }

   public String getVersion() {
      return version;
   }

   public void setVersion(String version) {
      this.version = version;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getLocation() {
      return location;
   }

   public void setLocation(String location) {
      this.location = location;
   }
}
