package io.spring.lab.warehouse.item;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
class ItemRepresentation {

    private Long id;

    private String name;

    private int count = 0;

    private BigDecimal price = ZERO;

    static ItemRepresentation of(Item item) {
        return new ItemRepresentation(item.getId(), item.getName(), item.getCount(), item.getPrice());
    }

    Item asItem() {
        return new Item(null, name, count, price);
    }
}

