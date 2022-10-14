package pl.sda.customersafterkurs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;



    @Test
    void shouldCreateOrder(){
        orderService.makeOrder("Terminator", "1234545");


    }

}