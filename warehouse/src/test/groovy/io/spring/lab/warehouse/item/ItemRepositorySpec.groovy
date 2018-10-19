package io.spring.lab.warehouse.item

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles;
import spock.lang.Specification;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
@ActiveProfiles(profiles = ["test", "h2"])
@AutoConfigureTestDatabase
class ItemRepositorySpec extends Specification {

    @Autowired
    private ItemRepository itemRepository;

    def "should find item by its prefix"() {
        given:

        itemRepository.save(new Item(999L, "item name", 10, BigDecimal.TEN))
        itemRepository.save(new Item(1000L, "item name2", 10, BigDecimal.TEN))

        when:
        def item = itemRepository.findByNamePrefix("item")

        then:
        item.size() == 2
        item.get(0).getName() == "item name"
        item.get(1).getName() == "item name2"
    }
}
