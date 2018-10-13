package io.spring.lab.warehouse.item;

import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.reflect.FieldUtils.writeField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

class StubItemRepository implements ItemRepository {

    private final AtomicLong seq = new AtomicLong();
    private final Map<Long, Item> db = new HashMap<>();

    @Override
    public Optional<Item> findOne(long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Item save(Item item) {
        long id = setAndGetNextId(item);
        db.put(id, item);
        return item;
    }

    @Override
    public Optional<Item> findMostExpensiveItem() {
        return db.values().stream().max(comparing(Item::getPrice));
    }

    @Override
    public void delete(long id) {
        db.remove(id);
    }

    private long setAndGetNextId(Item item) {
        try {
            long id = Optional.ofNullable(item.getId()).orElseGet(seq::incrementAndGet);
            writeField(item, "id", id, true);
            return id;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unexpected error!", e);
        }
    }
}
