package xwm.xwm.osgistarter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * See https://www.baeldung.com/kotlin/allopen-spring
 * I use the kotlin-maven-allopen plugin to open classes for Spring.
 * By default all classes are final in Kotlin. But Spring needs them
 * for @Configuration classes for example. Which @SpringBootApplication
 * automatically sets up.
 */
@SpringBootApplication
class Application

fun main(args: Array<String>) {
   runApplication<Application>(*args)
}