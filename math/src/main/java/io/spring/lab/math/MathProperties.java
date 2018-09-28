package io.spring.lab.math;

import static java.math.RoundingMode.HALF_EVEN;

import java.math.MathContext;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "math")
public class MathProperties {

    /** Precision for Math operations */
    private int precision = 18;

    /** Scale for Math operations */
    private int scale = 4;

    public MathContext getContext() {
        return new MathContext(precision, HALF_EVEN);
    }
}
