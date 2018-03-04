package com.xke.xebia.localstack.invoicing;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class InvoicingService {
    @JmsListener(destination = "invoices")
    public void processInvoice(String message) {
        System.out.println("INVOICE:" + message);
    }
}
