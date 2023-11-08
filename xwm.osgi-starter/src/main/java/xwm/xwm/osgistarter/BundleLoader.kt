package xwm.xwm.osgistarter

import org.eclipse.core.runtime.Platform
import org.eclipse.core.runtime.adaptor.EclipseStarter
import org.osgi.framework.Bundle
import org.osgi.framework.BundleContext
import org.osgi.framework.BundleException
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

class BundleLoader private constructor() {
   private var bc: BundleContext? = null

   //   private final Framework fwk;
   init {
      try {
         val config: MutableMap<String, String> = HashMap()
         config["org.osgi.framework.bootdelegation"] = "*"
         config["osgi.parentClassloader"] = "fwk"
         config["osgi.contextClassLoaderParent"] = "fwk"
         config["osgi.bundles"] = "org.eclipse.equinox.common@2:start,org.eclipse.update" +
            ".configurator@3:start,org.eclipse.core.jobs@4:start,org.eclipse.core.runtime@start," + "org.eclipse.equinox.registy@start"
         config["osgi.clean"] = "true"
         config["eclipse.noRegistryCache"] = "true"
         EclipseStarter.setInitialProperties(config)
         bc = EclipseStarter.startup(arrayOfNulls(0), null)

         val extensionPoints = Platform.getExtensionRegistry().extensionPoints
         println(extensionPoints)

         // TODO get Eclipse Platform for extension points
      } catch (e: Exception) {
         throw RuntimeException(e)
      }
   }

   companion object {
      private var instance = BundleLoader()

      @JvmStatic
      @Throws(BundleException::class)
      fun installBundle(file: String): Bundle {
         var file = file
         if (!file.startsWith("file:")) {
            file = "file:$file"
         }
         return instance.bc!!.installBundle(file)
      }

      @JvmStatic
      @Throws(BundleException::class)
      fun uninstallBundle(id: Long): Boolean {
         val first = Stream.of(*instance.bc!!.bundles)
            .filter { b: Bundle -> b.bundleId == id }
            .findFirst()
         return if (first.isPresent) {
            first.get()
               .uninstall()
            true
         } else {
            false
         }
      }

      @JvmStatic
      val bundles: List<Bundle>
         get() = Stream.of(
            *Optional.ofNullable(
               instance.bc!!.bundles
            )
               .orElse(arrayOfNulls(0))
         )
            .collect(Collectors.toList())
   }
}