package com.xke.xebia.localstack.invoicing;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class InvoicingService {
    @Autowired
    private AmazonDynamoDB dynamoDB;

    @JmsListener(destination = "invoices")
    public void processInvoice(String message) {
        dynamoDB.putItem(new PutItemRequest()
                .withTableName("Invoice")
                .addItemEntry("Body", new AttributeValue().withS(message)));
    }
}
