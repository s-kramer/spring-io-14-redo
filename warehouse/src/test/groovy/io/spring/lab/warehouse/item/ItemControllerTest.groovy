package io.spring.lab.warehouse.item

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.hamcrest.Matchers.hasItems
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectmaper

    @Autowired
    ItemRepository itemRepository;


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

        and:
        result.andExpect(jsonPath('$.name').value(('itemName')))
        result.andExpect(jsonPath('$.count').value((10)))
        result.andExpect(jsonPath('$.price').value(13.5))
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

}
