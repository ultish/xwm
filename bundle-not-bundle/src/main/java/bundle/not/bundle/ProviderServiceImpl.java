package bundle.not.bundle;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class ProviderServiceImpl implements ProviderService, BundleActivator {
	 
    private ServiceReference<ProviderService> serviceReference;
 
    private ServiceRegistration<ProviderService> registration;
 
    @Override
    public String provide(String type) {
        return this.getClass().getName() + " - Providing - Type " + type;
    }
 
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Start method called on the Provider Bundle");
        registration = context.registerService(
            ProviderService.class, new ProviderServiceImpl(),
            new Hashtable<>());
        serviceReference = registration.getReference();
        System.out.println("Started Provider Bundle Successfully with id "
            + serviceReference.getBundle().getBundleId());
    }
 
    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Stop method called on the Provider Bundle");
        registration.unregister();
    }
}