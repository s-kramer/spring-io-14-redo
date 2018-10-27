package io.spring.lab.warehouse.item;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ItemService {

	private final ItemRepository items;
	private final MeterRegistry meterRegistry;

	public Item findOne(long id) {
		Optional<Item> item = items.findOne(id);
        meterRegistry.counter("item.service.search." + id + ".count").increment();
		return item.orElseThrow(() -> new ItemNotFound(id));
	}

	public Item findMostExpensiveItem() {
        final Optional<Item> mostExpensiveItem = items.findMostExpensiveItem();
        meterRegistry.counter("item.service.search.by.expensiveness.count").increment();
        return mostExpensiveItem.orElseThrow(() -> new IllegalArgumentException("No items in repository"));
    }

	public List<Item> findAll() {
        meterRegistry.counter("item.service.search.all.count").increment();
		return items.findAll();
	}

	public Item create(Item item) {
        final Item save = items.save(item);
        meterRegistry.counter("item.service.creation.count").increment();
        return save;
	}

	public Item update(ItemUpdate changes) {
		Validate.notNull(changes, "Changes cannot be null");

		Item item = findOne(changes.getId());
		item.update(changes);
        final Item save = items.save(item);
        meterRegistry.counter("item.service.update.count").increment();
        return save;
	}

	public Item updateStock(ItemStockUpdate changes) {
		Validate.notNull(changes, "Changes cannot be null");

		Item item = findOne(changes.getId());
		item.updateStock(changes);

        meterRegistry.counter("item.service.stock.update.count").increment();
		return items.save(item);
	}

    public void deleteItem(long itemId) {
        items.delete(itemId);
        meterRegistry.counter("item.service.deletion.count").increment();
    }
}

