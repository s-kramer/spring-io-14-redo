package io.spring.lab.marketing.special;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author created: skramer on 23.09.18 22:53
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SpecialRepresentation {
    private String id;

    private long itemId;

    private int batchSize = 1;

    private BigDecimal batchPrice = ZERO;

    public static SpecialRepresentation of(Special special) {
        return SpecialRepresentation.builder()
                .id(special.getId())
                .itemId(special.getItemId())
                .batchPrice(special.getBatchPrice())
                .batchSize(special.getBatchSize())
                .build();
    }
}
