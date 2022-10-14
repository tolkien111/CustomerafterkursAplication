package pl.sda.customersafterkurs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
