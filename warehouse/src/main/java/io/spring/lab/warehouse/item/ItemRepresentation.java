package io.spring.lab.warehouse.item;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.Validate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRepresentation {

	private Long id;

	private String name;

	private int count = 0;

	private BigDecimal price = ZERO;

	public static ItemRepresentation of(Item item) {
        return new ItemRepresentation(item.getId(), item.getName(), item.getCount(), item.getPrice());
    }
}
