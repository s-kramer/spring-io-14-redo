package io.spring.lab.marketing;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.List;

import io.spring.lab.marketing.special.SpecialRepresentation;
import io.spring.lab.marketing.special.SpecialService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
class SpecialsController {
    private SpecialService specialService;

    @GetMapping(value = "/special", produces = APPLICATION_JSON_UTF8_VALUE)
    public List<SpecialRepresentation> getSpecials() {
        return specialService.findAll()
                .stream().map(SpecialRepresentation::of).collect(toList());
    }
}
