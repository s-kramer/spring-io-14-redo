package io.spring.lab.warehouse.item

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import spock.lang.Specification

import static io.spring.lab.warehouse.ItemDataInitializer.itemsTestData

class ItemServiceSpec extends Specification {

    ItemRepository repository = new StubItemRepository()

    Counter mockCounter = Mock(Counter.class)
    private MeterRegistry mockMeterRegistry = Mock(MeterRegistry.class)
    ItemService items = new ItemService(repository, mockMeterRegistry)

    void setup() {
        itemsTestData(repository)
        mockMeterRegistry.counter(_ as String) >> mockCounter
    }

    def  "should find all items" () {
        when:
            def items = items.findAll()
        then:
            items.size() >= 4
    }

    def "should not update not existing item"() {
        when:
            items.update(new ItemUpdate(123L, 'test', 23.99))
        then:
            thrown ItemNotFound
    }

    def "should update item details"() {
        when:
            items.update(new ItemUpdate(1L, 'test', 23.99))
        then:
            Item item = items.findOne(1L)
            item.name == "test"
            item.price == 23.99
    }

    def "should not check out too many items"() {
        when:
           items.updateStock(new ItemStockUpdate(1L, -120))
        then:
            thrown OutOfStock
    }

    def "should check out some items"() {
        when:
            items.updateStock(new ItemStockUpdate(1L, -20))
        then:
            Item item = items.findOne(1L)
            item.count == 80
    }
}
