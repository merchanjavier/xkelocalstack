package com.cloud.reach.localstack.invoicing;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
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
    }
}
