package io.spring.lab.math;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MathProperties.class)
@ConditionalOnMissingBean(MathProperties.class)
class MathAutoConfiguration {

}
