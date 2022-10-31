package pl.sda.customersafterkurs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.customersafterkurs.entity.Company;
import pl.sda.customersafterkurs.entity.CustomerRepository;
import pl.sda.customersafterkurs.service.dto.RegisterCompanyForm;
import pl.sda.customersafterkurs.service.exception.EmailAlreadyExistsException;
import pl.sda.customersafterkurs.service.exception.VatAlreadyExistsException;

import javax.transaction.Transactional;

import java.lang.reflect.Executable;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    @Autowired
    private CustomerRepository repository;



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

    @Test
    void shouldNotRegisterCompanyIfEmailExists(){
        //Given
        repository.saveAndFlush(new Company("dds@gmail.com","HP", "PL999324234"));
        final var form = new RegisterCompanyForm("IBM", "PL99930223", "dds@gmail.com");

        //When & Then
        assertThrows(EmailAlreadyExistsException.class ,() -> service.registerCompany(form));

    }

    @Test
    void shouldNotRegisterCompanyIfVatExists(){
        //Given
        repository.saveAndFlush(new Company("dds@gmail.com","HP", "PL99930223"));
        final var form = new RegisterCompanyForm("IBM", "PL99930223", "daaas@gmail.com");

        //When & Then
        assertThrows(VatAlreadyExistsException.class,() -> service.registerCompany(form)); // labda wykonuje kod, zostaje przechwycony wyjątek i podónany z wyjątkiem wejściowym

    }
}