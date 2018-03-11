package com.xke.xebia.localstack.fanout;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.jayway.restassured.RestAssured;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class FanoutControllerTest {

    private static final String INVOICE = "Invoice";
    private static final String ORDER = "Order";

    static {
        RestAssured.port = 8989;
    }

    @Autowired
    private AmazonDynamoDB dynamoDB;

    @Test
    public void should_fanout_items_to_invoices_and_orders() throws Exception {
        // given
        Integer invoiceCount = getScanCount(INVOICE);
        Integer orderCount = getScanCount(ORDER);

        // when
        given().post("/items/AZERTY");

        // then
        await().until(() -> getScanCount(INVOICE) > invoiceCount);
        await().until(() -> getScanCount(ORDER) > orderCount);
        assertThat(getScanCount(INVOICE)).isGreaterThan(invoiceCount);
        assertThat(getScanCount(ORDER)).isGreaterThan(orderCount);
    }

    private Integer getScanCount(String tableName) {
        return dynamoDB.scan(new ScanRequest().withTableName(tableName)).getCount();
    }
}