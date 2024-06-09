package cz.krystofcejchan.food_and_order_middleware.configuration;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCrossOriginConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("http://localhost:4200");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebCrossOriginConfiguration();
    }
}
