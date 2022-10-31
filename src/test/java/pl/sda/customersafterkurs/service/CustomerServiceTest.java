package pl.sda.customersafterkurs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.customersafterkurs.entity.CustomerRepository;
import pl.sda.customersafterkurs.service.dto.RegisterCompanyForm;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    @Autowired
    CustomerRepository repository;



    @Test
    void shouldRegisterCompany(){
        //Given
        final var form = new RegisterCompanyForm("IBM", "PL99930223", "dds@gmail.com");

        //When
        final var customerId = service.registerCompany(form);
        final var customer = repository.findByEmailEndingWithIgnoreCase("dds@gmail.com");

        //Then
        assertNotNull(customerId);
        assertEquals(customerId.getId(), customer.get(0).getId()); // moje sprawdzenie
        assertTrue(repository.existsById(customerId.getId()));
    }

}