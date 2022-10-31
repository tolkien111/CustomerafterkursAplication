package pl.sda.customersafterkurs.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.customersafterkurs.demo.OrderService;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;



    @Test
    void shouldCreateOrder(){
        orderService.makeOrder("Terminator", "1234545");


    }

}