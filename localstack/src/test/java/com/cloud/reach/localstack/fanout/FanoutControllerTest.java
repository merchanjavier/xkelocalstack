package com.cloud.reach.localstack.fanout;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FanoutControllerTest {
    @Autowired
    private AmazonSNS amazonSNS;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Test
    public void should_fanout_items_to_invoices_and_orders() throws Exception {
        // given
        Integer invoiceCountBefore = getScanCount("Invoice").call();
        Integer orderCountBefore = getScanCount("Order").call();

        // when
        amazonSNS.publish(new PublishRequest("arn:aws:sns:eu-west-1:123456789012:items", "Cloudreach rules!"));
        await().atMost(5, SECONDS).until(getScanCount("Invoice"), greaterThan(invoiceCountBefore));
        await().atMost(5, SECONDS).until(getScanCount("Order"), greaterThan(orderCountBefore));

        // then
        assertThat(getScanCount("Invoice").call()).isGreaterThan(invoiceCountBefore);
        assertThat(getScanCount("Order").call()).isGreaterThan(orderCountBefore);
    }

    private Callable<Integer> getScanCount(final String tableName) {
        return () -> {
            ScanRequest scanRequest = new ScanRequest().withTableName(tableName);
            ScanResult result = amazonDynamoDB.scan(scanRequest);
            return result.getCount();
        };
    }
}