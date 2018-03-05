package com.xke.xebia.localstack.ordering;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class OrderingService {
    private AmazonDynamoDB amazonDynamoDB;

    public OrderingService(AmazonDynamoDB amazonDynamoDB) {
        this.amazonDynamoDB = amazonDynamoDB;
    }

    @JmsListener(destination = "orders")
    public void processOrder(String message) {
        new DynamoDB(amazonDynamoDB)
                .getTable("Order")
                .putItem(new Item().withString("Body", message));
    }
}
