package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GreetingApplicationRunListener implements SpringApplicationRunListener, Ordered {

    private final SpringApplication app;
    private final String[] args;

    public GreetingApplicationRunListener(SpringApplication app, String[] args) {
        this.app = app;
        this.args = args;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public void starting() {
        log.info("### starting {} with args {}", app.getMainApplicationClass().getSimpleName(), Arrays.toString(args));
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        log.info("### environmentPrepared {} with args {}", app.getMainApplicationClass().getSimpleName(), Arrays.toString(args));
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("### contextPrepared {} with args {}", app.getMainApplicationClass().getSimpleName(), Arrays.toString(args));
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("### contextLoaded {} with args {}", app.getMainApplicationClass().getSimpleName(), Arrays.toString(args));
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        log.info("### started {} with args {}", app.getMainApplicationClass().getSimpleName(), Arrays.toString(args));
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        log.info("### running {} with args {}", app.getMainApplicationClass().getSimpleName(), Arrays.toString(args));
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info("### failed {} with args {}", app.getMainApplicationClass().getSimpleName(), Arrays.toString(args));
    }
}
