package spring.observability

import io.micrometer.observation.annotation.Observed
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
  class MyUserService {
   private val log = LoggerFactory.getLogger(this::class.java)

   // Example of using an annotation to observe methods
   // <user.name> will be used as a metric name
   // <getting-user-name> will be used as a span  name
   // <userType=userType2> will be set as a tag for both metric & span
   @Observed(
      name = "user.name",
      contextualName = "getting-user-name",
      lowCardinalityKeyValues = ["userType", "userType2"]
   )
   fun userName(userId: String?): String {
      log.info("Getting user name for user with id <{}>", userId)
      try {
         Thread.sleep(Random.nextLong(200L)) // simulates latency
      } catch (e: InterruptedException) {
         throw RuntimeException(e)
      }
      return "foo"
   }

}