package de.apnmt.aws.common.config;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.core.env.ResourceIdResolver;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

@Configuration
public class SnsConfiguration {

    @Value("${cloud.aws.sqs.endpoint}")
    private String endpoint;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AwsClientBuilder.EndpointConfiguration snsEndpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(endpoint, region);
    }

    @Bean
    public NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNS amazonSNS, MessageConverter messageConverter) {
        return new NotificationMessagingTemplate(amazonSNS, (ResourceIdResolver) null, messageConverter);
    }

    @Bean
    protected MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setSerializedPayloadClass(String.class);
        converter.setStrictContentTypeMatch(false);
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    @Primary
    public AmazonSNS amazonSNS(AWSCredentialsProviderChain credentials) {
        return AmazonSNSClientBuilder.standard().withEndpointConfiguration(snsEndpointConfiguration()).withCredentials(credentials).build();
    }

}
