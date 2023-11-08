package spring.observability

import io.micrometer.observation.ObservationRegistry
import io.micrometer.observation.aop.ObservedAspect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class MyConfiguration {
   // To have the @Observed support we need to register this aspect
   @Bean
   fun observedAspect(observationRegistry: ObservationRegistry?): ObservedAspect {
      return ObservedAspect(observationRegistry!!)
   }
}