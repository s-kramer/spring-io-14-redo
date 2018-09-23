package io.spring.lab.marketing;

import java.math.BigDecimal;

import io.spring.lab.marketing.special.Special;
import io.spring.lab.marketing.special.SpecialRepository;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(scanBasePackages = "io.spring.lab")
public class MarketingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketingApplication.class, args);
    }
}

@Configuration
class RepositoryInitializer {
    private SpecialRepository specials;

    public ApplicationRunner setSpecials() {
        return args -> {
            specials.save(new Special(null, 1L, 3, BigDecimal.valueOf(70.0)));
            specials.save(new Special(null, 2L, 2, BigDecimal.valueOf(15.0)));
            specials.save(new Special(null, 3L, 4, BigDecimal.valueOf(60.0)));
            specials.save(new Special(null, 4L, 2, BigDecimal.valueOf(40.0)));
        };
    }
}
