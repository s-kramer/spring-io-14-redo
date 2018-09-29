package io.spring.lab.warehouse.item

import com.fasterxml.jackson.databind.ObjectMapper
import io.spring.lab.warehouse.ItemDataInitializator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectmaper

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
        result.andExpect(status().is2xxSuccessful())

        and:
        result.andExpect(jsonPath('$.name').value(('itemName')))
        result.andExpect(jsonPath('$.count').value((10)))
        result.andExpect(jsonPath('$.price').value(13.5))
    }

    @TestConfiguration
    static class SpringSpecConfig {

        @Autowired
        ItemRepository items

        @Bean
        ApplicationRunner init() {
            return { args ->
                ItemDataInitializator.itemsTestData(items)
            } as ApplicationRunner
        }
    }
}
