package com.xke.xebia.localstack.fanout;

import com.jayway.restassured.RestAssured;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.TestCase.fail;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class FanoutControllerTest {
    static {
        RestAssured.port = 8989;
    }

    @Test
    public void should_fanout_items_to_invoices_and_orders() throws Exception {
        // given

        // when
        given().post("/items/AZERTY");

        // then
        fail();
    }
}