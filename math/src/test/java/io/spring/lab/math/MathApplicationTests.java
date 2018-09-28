package io.spring.lab.math;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"math.precision=99", "math.scale=88"})
public class MathApplicationTests {

    @Autowired
    private MathProperties mathProperties;

    @Test
    public void contextLoads() {
    }

    @Test
    public void shouldReadProvidedProperties() {
        assertThat(mathProperties.getPrecision(), is(99));
        assertThat(mathProperties.getScale(), is(88));
    }

}
