package cz.krystofcejchan.food_and_order_middleware.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class WebCrossOriginConfiguration : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins("http://localhost:4200")
    }

    @Bean
    open fun corsConfigurer(): WebMvcConfigurer {
        return WebCrossOriginConfiguration()
    }
}
