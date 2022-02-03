package de.apnmt.aws.common.config;

import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
@ConditionalOnProperty(
        value = "cloud.aws.tracing,xray.enabled",
        havingValue = "true")
public class XRayConfiguration {

    @Value("${spring.application.name}")
    public String appName;

    @Bean
    public Filter tracingFilter() {
        return new AWSXRayServletFilter(appName);
    }

}
