package pl.sda.customersafterkurs.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.customersafterkurs.demo.CustomerRegistration;

@SpringBootTest
public class CustomerRegistrationTest {

    @Autowired
    private CustomerRegistration customerRegistration;

    @Test
    void testRegisterCustomer() {
        //Given
        //When
        customerRegistration.register("jan@wp.pl", "Jan Kowalski");
        //Then

    }
}
