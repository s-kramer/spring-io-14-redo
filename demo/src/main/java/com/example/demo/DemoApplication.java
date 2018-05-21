package com.example.demo;

import static java.util.Collections.singleton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication();
        app.setSources(singleton(DemoApplication.class.getCanonicalName()));
        app.setDefaultProperties(defaultProperties());
        app.setAdditionalProfiles("profile1", "profile2");
        app.run(args);
    }

    private static Map<String, Object> defaultProperties() {
        Map<String, Object> defaultProperties = new HashMap<>();
        defaultProperties.put("server.port", 9004);
        return defaultProperties;
    }
}

@Slf4j
@RestController
class GreetingController implements ApplicationRunner {

    @Value("${greeting.template:}")
    String template;

    @PostConstruct
    void requireTemplate() {
        if (!StringUtils.hasText(template)) {
            throw new GreetingTemplateRequired();
        }
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Defined greeting template: {}", template);
    }

    @GetMapping("/greetings/{name}")
    Greeting greet(@PathVariable("name") String name) {
        return new Greeting(String.format(template, name));
    }
}

@lombok.Value
class Greeting {

	private String message;

    @JsonCreator
    static Greeting of(@JsonProperty("message") String message) {
        return new Greeting(message);
    }
}

class GreetingTemplateRequired extends RuntimeException implements ExitCodeGenerator {

    @Override
    public int getExitCode() {
        return 11;
    }
}

@Component
class GreetingFailureAnalyzer extends AbstractFailureAnalyzer<GreetingTemplateRequired> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, GreetingTemplateRequired cause) {
        return new FailureAnalysis(
                "Greeting template undefined.",
                "Define greeting.template property.",
                rootFailure);
    }
}

