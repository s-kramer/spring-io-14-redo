package io.spring.lab.warehouse.item;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ItemService {

	private final ItemRepository items;

	public Item findOne(long id) {
		Optional<Item> item = items.findOne(id);
		return item.orElseThrow(() -> new ItemNotFound(id));
	}

	public Item findMostExpensiveItem() {
        return items.findMostExpensiveItem().orElseThrow(() -> new IllegalArgumentException("No items in repository"));
    }

	public List<Item> findAll() {
		return items.findAll();
	}

	public Item create(Item item) {
		return items.save(item);
	}

	public Item update(ItemUpdate changes) {
		Validate.notNull(changes, "Changes cannot be null");

		Item item = findOne(changes.getId());
		item.update(changes);
		return items.save(item);
	}

	public Item updateStock(ItemStockUpdate changes) {
		Validate.notNull(changes, "Changes cannot be null");

		Item item = findOne(changes.getId());
		item.updateStock(changes);

		return items.save(item);
	}

    public void deleteItem(long itemId) {
        items.delete(itemId);
    }
}

