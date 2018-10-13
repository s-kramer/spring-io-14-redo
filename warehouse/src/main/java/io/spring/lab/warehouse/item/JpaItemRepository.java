package io.spring.lab.warehouse.item;

import java.util.List;
import java.util.Optional;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

/**
 * @author created: skramer on 12.10.18 21:06
 */
@Repository
@AllArgsConstructor
class JpaItemRepository implements ItemRepository {
    SpringCrudItemRepository springCrudItemRepository;

    @Override
    public Optional<Item> findOne(long id) {
        return Try.of(() -> springCrudItemRepository.findOne(id))
                .toJavaOptional();
    }

    @Override
    public List<Item> findAll() {
        return Lists.newArrayList(springCrudItemRepository.findAll());
    }

    @Override
    public Item save(Item item) {
        return springCrudItemRepository.save(item);
    }

    @Override
    public Optional<Item> findMostExpensiveItem() {
        return Optional.ofNullable(springCrudItemRepository.findTopByOrderByPriceDesc());
    }

    @Override
    public void delete(long id) {
        springCrudItemRepository.delete(id);
    }

    @Override
    public void deleteAllItems() {
        springCrudItemRepository.deleteAll();
    }

    interface SpringCrudItemRepository extends CrudRepository<Item, Long> {
        Item findTopByOrderByPriceDesc();
    }


}
