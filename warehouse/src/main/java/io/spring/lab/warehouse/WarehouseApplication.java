package io.spring.lab.warehouse;

import java.util.Random;

import io.spring.lab.warehouse.item.ItemRepository;
import lombok.AllArgsConstructor;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class WarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }
}

@ConditionalOnProperty(value = "test.data", matchIfMissing = true, havingValue = "true")
@Component
@AllArgsConstructor
class ItemInitializer implements ApplicationRunner {
    ItemRepository items;

    @Override
    public void run(ApplicationArguments args) {
        ItemDataInitializer.itemsTestData(items);
    }
}

@Configuration
@EnableJpaRepositories(considerNestedRepositories = true)
class ApplicationConfiguration {
    private Random random = new Random();

    @Bean
    HealthIndicator randomHealthIndicator() {
        return () -> random.nextBoolean()
                ? Health.up().build()
                : Health.down().withDetail("random", random.nextInt()).build();
    }

}


