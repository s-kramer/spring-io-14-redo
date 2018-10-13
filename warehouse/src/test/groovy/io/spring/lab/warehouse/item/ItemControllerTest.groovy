package io.spring.lab.warehouse.item

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import static org.hamcrest.Matchers.comparesEqualTo
import static org.hamcrest.Matchers.containsString
import static org.hamcrest.Matchers.hasItems
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.stringContainsInOrder
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = ["test"])
@AutoConfigureTestDatabase
class ItemControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectmaper

    @Autowired
    ItemRepository itemRepository


    def "should find all items in the repository"() {
        expect:
        mockMvc.perform(get("/item")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.length()').value(4))
                .andExpect(jsonPath('$.[*].name').value(hasItems('A', 'B', 'C', 'D')))
    }

    def "should create item and add it to repository"() {
        given:
        Map request = [
                name : "itemName",
                price: 13.5,
                count: 10
        ]

        when:
        def result = mockMvc.perform(post("/item")
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectmaper.writeValueAsString(request)))

        then:
        result.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("location", containsString("/item/5")))
    }

    def "should find item by id"() {
        expect:
        mockMvc.perform(get("/item/2").contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id').isNotEmpty())
                .andExpect(jsonPath('$.name').value('B'))
                .andExpect(jsonPath('$.count').value(100))
                .andExpect(jsonPath('$.price').value(10))
    }

    def "should throw exception when requesting item that does not exist"() {
        expect:
        mockMvc.perform(get("/item/1234567890").contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath('$.message').value("Item 1234567890 does not exist"))
    }

    def "should update item"() {
        final content = [name: 'updated']
        final body = objectmaper.writeValueAsString(content)

        expect:
        mockMvc.perform(put("/item/3").contentType(APPLICATION_JSON_UTF8).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.name').value('updated'))

        final modifiedContent = [name: 'modified name']
        final modifiedBody = objectmaper.writeValueAsString(modifiedContent)

        and:
        mockMvc.perform(put("/item/3").contentType(APPLICATION_JSON_UTF8).content(modifiedBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.name').value('modified name'))
    }

    def "should update item stock"() {
        final content = [countDiff: 100]
        final body = objectmaper.writeValueAsString(content)

        expect:
        mockMvc.perform(put("/item/3/stock").contentType(APPLICATION_JSON_UTF8).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.count').isNotEmpty())
                .andExpect(jsonPath('$.id').isNotEmpty())

        final modifiedContent = [countDiff: -3000]
        final modifiedBody = objectmaper.writeValueAsString(modifiedContent)

        and:
        mockMvc.perform(put("/item/3/stock").contentType(APPLICATION_JSON_UTF8).content(modifiedBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.message').value(stringContainsInOrder(['Item', 'has only', 'out of -3000 requested'])))
    }

    def "should remove given item"() {
        given:
        mockMvc.perform(get("/item/5").contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())

        when:
        mockMvc.perform(delete("/item/5"))
                .andExpect(status().isOk())

        then:
        mockMvc.perform(get("/item/5").contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())

    }

    def "should find item with maximum price"() {
        given:
        BigDecimal maxPrice = getMaxItemPrice()

        when:
        def result = mockMvc.perform(get("/item/top").contentType(APPLICATION_JSON_UTF8))

        then:
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$.price', comparesEqualTo(maxPrice.toDouble())))
    }

    def "should throw when no items are available when querying for item with maximum price"() {
        given:
        itemRepository.deleteAllItems()

        when:
        def result = mockMvc.perform(get("/item/top").contentType(APPLICATION_JSON_UTF8))

        then:
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.message', is("No items in repository")))

    }

    private BigDecimal getMaxItemPrice() {
        List<ItemRepresentation> currentItems = getAllItems()
        return currentItems.stream().max { a, b -> a.price <=> b.price }.get().price
    }

    private List<ItemRepresentation> getAllItems() {
        def responseBody = mockMvc.perform(get("/item").contentType(APPLICATION_JSON_UTF8))
                .andReturn().response.getContentAsString()
        return objectmaper.readValue(responseBody, new TypeReference<List<ItemRepresentation>>() {})
    }

}
