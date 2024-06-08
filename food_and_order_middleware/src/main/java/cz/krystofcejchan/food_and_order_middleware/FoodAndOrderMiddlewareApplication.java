package cz.krystofcejchan.food_and_order_middleware;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class FoodAndOrderMiddlewareApplication {

    public static void main(String... args) {
        SpringApplication.run(FoodAndOrderMiddlewareApplication.class, args);
    }

    /*@Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
        return args -> {
            kafkaTemplate.send("topic1", "data7989191°ř");
        };
    }*/
}
