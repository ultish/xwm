package spring.observability

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MyController {
   @Autowired
   lateinit var myUserService: MyUserService

   private val log = LoggerFactory.getLogger(this::class.java)

   @GetMapping("/user/{userId}")
   fun userName(@PathVariable("userId") userId: String?): String {
      log.info("Got a request")
      return this.myUserService.userName(userId)
   }

}