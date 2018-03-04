package com.xke.xebia.localstack.fanout;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FanoutController {
    private AmazonSNS amazonSNS;

    public FanoutController(AmazonSNS amazonSNS) {
        this.amazonSNS = amazonSNS;
    }

    @GetMapping
    public String index() {
        return "https://docs.aws.amazon.com/fr_fr/sns/latest/dg/SNS_Scenarios.html";
    }

    @PostMapping("/items/{itemId}")
    public void createItem(@PathVariable String itemId) {
        amazonSNS.publish(new PublishRequest("arn:aws:sns:us-east-1:123456789012:items", itemId));
    }
}
