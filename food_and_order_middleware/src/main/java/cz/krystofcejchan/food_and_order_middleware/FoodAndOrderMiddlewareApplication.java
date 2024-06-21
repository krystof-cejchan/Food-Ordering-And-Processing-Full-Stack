package cz.krystofcejchan.food_and_order_middleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class FoodAndOrderMiddlewareApplication {

    public static void main(String... args) {
        SpringApplication.run(FoodAndOrderMiddlewareApplication.class, args);
    }
}
