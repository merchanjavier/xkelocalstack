package com.xke.xebia.localstack.ordering;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class OrderingService {
    @Autowired
    private AmazonDynamoDB dynamoDB;

    @JmsListener(destination = "orders")
    public void processOrder(String message) {
        dynamoDB.putItem(new PutItemRequest()
                .withTableName("Order")
                .addItemEntry("Body", new AttributeValue().withS(message)));
    }
}
