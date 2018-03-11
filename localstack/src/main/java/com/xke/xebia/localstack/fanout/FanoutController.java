package com.xke.xebia.localstack.fanout;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FanoutController {
    @GetMapping
    public String index() {
        return "https://docs.aws.amazon.com/fr_fr/sns/latest/dg/SNS_Scenarios.html";
    }

    @PostMapping("/items/{itemId}")
    public void createItem(@PathVariable String itemId) {
    }
}
