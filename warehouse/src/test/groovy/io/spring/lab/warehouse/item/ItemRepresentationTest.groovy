package io.spring.lab.warehouse.item

import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.json.JsonContent
import spock.lang.Specification

@JsonTest
class ItemRepresentationTest extends Specification {
    @Autowired
    private JacksonTester<ItemRepresentation> jacksonTester;

    def "should serialize ItemRepresentation object"() {
        given:
        def representation = new ItemRepresentation(1L, "name", 100, BigDecimal.TEN)

        when:
        JsonContent<ItemRepresentation> result = jacksonTester.write(representation)

        then:
        Assertions.assertThat(result).extractingJsonPathNumberValue('$.id').isEqualTo(1)
        Assertions.assertThat(result).extractingJsonPathStringValue('$.name').isEqualTo("name")
        Assertions.assertThat(result).extractingJsonPathNumberValue('$.count').isEqualTo(100)
        Assertions.assertThat(result).extractingJsonPathNumberValue('$.price').isEqualTo(10)
    }

    def "should deserialize ItemRepresentation json string"() {
        def json = """{"id":1,"name":"name","count":100,"price":10}"""

        when:
        final itemRepresentation = jacksonTester.parseObject(json)

        then:
        itemRepresentation.id == 1L
        itemRepresentation.name == "name"
        itemRepresentation.count == 100
        itemRepresentation.price == BigDecimal.TEN

    }

}
