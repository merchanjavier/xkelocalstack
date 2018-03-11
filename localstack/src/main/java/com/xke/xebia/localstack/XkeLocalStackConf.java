package com.xke.xebia.localstack;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import static com.amazonaws.regions.Regions.US_EAST_1;

@Configuration
public class XkeLocalStackConf {
    private static final String LOCALSTACK_SNS = "http://localhost:4575";
    private static final String LOCALSTACK_SQS = "http://localhost:4576";
    private static final String LOCALSTACK_DYNAMODB = "http://localhost:4569";
    private static final String LOCAL = "local";

    @Value("${environment}")
    private String environment;

    @Bean
    public AmazonSNS getAmazonSNS() {
        AmazonSNSClientBuilder clientBuilder = AmazonSNSClientBuilder.standard();

        if (environment.equals(LOCAL)) {
            clientBuilder.withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(
                            LOCALSTACK_SNS,
                            US_EAST_1.getName()));
        }

        return clientBuilder.build();
    }

    @Bean
    public AmazonSQS getAmazonSQS() {
        AmazonSQSClientBuilder clientBuilder = AmazonSQSClientBuilder.standard();

        if (environment.equals(LOCAL)) {
            clientBuilder.withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(
                            LOCALSTACK_SQS,
                            US_EAST_1.getName()));
        }

        return clientBuilder.build();
    }

    @Bean
    public AmazonDynamoDB getAmazonDynamoDB() {
        AmazonDynamoDBClientBuilder clientBuilder = AmazonDynamoDBClientBuilder.standard();

        if (environment.equals(LOCAL)) {
            clientBuilder.withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(
                            LOCALSTACK_DYNAMODB,
                            US_EAST_1.getName()));
        }

        return clientBuilder.build();
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(
                new SQSConnectionFactory(
                        new ProviderConfiguration(),
                        getAmazonSQS()));
        return factory;
    }
}