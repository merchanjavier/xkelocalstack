package com.xke.xebia.localstack.ordering;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class OrderingService {
    @JmsListener(destination = "orders")
    public void processOrder(String message) {
        System.out.println("ORDER:" + message);
    }
}
