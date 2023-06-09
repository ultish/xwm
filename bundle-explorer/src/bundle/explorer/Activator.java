package bundle.explorer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Bundle Started");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("Bundle Stopped");
		
	}

}
