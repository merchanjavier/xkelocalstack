package com.xke.xebia.localstack;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@org.springframework.context.annotation.Configuration
public class Configuration {
    private AmazonSQSClientBuilder amazonSQSClientBuilder = AmazonSQSClientBuilder.standard()
            .withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(
                            "http://localhost:4576",
                            "us-east-1"));

    @Bean
    public AmazonSQS getAmazonSQS() {
        return amazonSQSClientBuilder.build();
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(
                new SQSConnectionFactory(
                        new ProviderConfiguration(),
                        amazonSQSClientBuilder));
        return factory;
    }

    @Bean
    public AmazonSNS getAmazonSNS() {
        return AmazonSNSClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                "http://localhost:4575",
                                "us-east-1"))
                .build();
    }
}