package io.spring.lab.marketing;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import io.spring.lab.marketing.special.Special;
import io.spring.lab.marketing.special.SpecialService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author created: skramer on 23.09.18 22:10
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(SpecialsController.class)
public class SpecialsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SpecialService specialService;

    @Test
    public void shouldFindSpecials() throws Exception {
        doReturn(singletonList(new Special("A", 1L, 15, BigDecimal.TEN))).when(specialService).findAll();

        mockMvc.perform(get("/special").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", is("A")))
                .andExpect(jsonPath("$.[0].batchSize", is(15)));
    }
}
