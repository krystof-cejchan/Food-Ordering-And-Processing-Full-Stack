package cz.krystofcejchan.food_status_middleware.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class JPA {
    /**
     * MySQL server connection bean
     *
     * @return DataSource
     */
    @Primary
    @Bean
    public DataSource getPrimaryDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/food_processing_and_order")
                .username("root")
                .password(System.getenv("password")).build();
    }
}
