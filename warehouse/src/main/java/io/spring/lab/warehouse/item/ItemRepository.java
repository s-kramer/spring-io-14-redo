package io.spring.lab.warehouse.item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Optional<Item> findOne(long id);

    List<Item> findAll();

    Item save(Item item);

    Optional<Item> findMostExpensiveItem();

    void delete(long id);

    void deleteAllItems();

    List<Item> findByNamePrefix(String prefix);
}
