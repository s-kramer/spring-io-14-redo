package io.spring.lab.warehouse;

import java.math.BigDecimal;

import io.spring.lab.warehouse.item.Item;
import io.spring.lab.warehouse.item.ItemRepository;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemDataInitializer {

    public static void itemsTestData(ItemRepository items) {
        items.save(new Item(null, "A", 100, BigDecimal.valueOf(40.0)));
        items.save(new Item(null, "B", 100, BigDecimal.valueOf(10.0)));
        items.save(new Item(null, "C", 100, BigDecimal.valueOf(30.0)));
        items.save(new Item(null, "D", 100, BigDecimal.valueOf(25.0)));
    }
}
