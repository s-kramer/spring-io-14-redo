package io.spring.lab.warehouse;

import io.spring.lab.warehouse.item.ItemRepository;
import lombok.AllArgsConstructor;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class WarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }
}

@Component
@AllArgsConstructor
class ItemInitializer implements ApplicationRunner {
    ItemRepository itemRepository;

    @Override
    public void run(ApplicationArguments args) {
        ItemDataInitializator.itemsTestData(itemRepository);
    }
}
