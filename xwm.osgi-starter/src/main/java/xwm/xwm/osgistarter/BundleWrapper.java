package xwm.xwm.osgistarter;

import org.osgi.framework.Bundle;

public class BundleWrapper {
   String version;
   String name;
   String location;
   String state;
   long id;

   public BundleWrapper(Bundle b) {
      if (b != null) {
         id = b.getBundleId();
         version = b.getVersion()
            .toString();
         name = b.getSymbolicName();
         location = b.getLocation();

         switch (b.getState()) {
            case Bundle.ACTIVE:
               state = "active";
               break;
            case Bundle.INSTALLED:
               state = "installed";
               break;
            case Bundle.RESOLVED:
               state = "resolved";
               break;
            case Bundle.UNINSTALLED:
               state = "uninstalled";
               break;
         }
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

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }
}
