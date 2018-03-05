package com.xke.xebia.localstack.invoicing;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class InvoicingService {
    private AmazonDynamoDB amazonDynamoDB;

    public InvoicingService(AmazonDynamoDB amazonDynamoDB) {
        this.amazonDynamoDB = amazonDynamoDB;
    }

    @JmsListener(destination = "invoices")
    public void processInvoice(String message) {
        new DynamoDB(amazonDynamoDB)
                .getTable("Invoice")
                .putItem(new Item().withString("Body", message));
    }
}
