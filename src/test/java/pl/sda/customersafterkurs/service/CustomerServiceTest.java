package pl.sda.customersafterkurs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.customersafterkurs.entity.Company;
import pl.sda.customersafterkurs.entity.CustomerRepository;
import pl.sda.customersafterkurs.entity.Person;
import pl.sda.customersafterkurs.service.dto.RegisterCompanyForm;
import pl.sda.customersafterkurs.service.dto.RegisterPersonForm;
import pl.sda.customersafterkurs.service.exception.EmailAlreadyExistsException;
import pl.sda.customersafterkurs.service.exception.PeselAlreadyExistsException;
import pl.sda.customersafterkurs.service.exception.PeselNotValidateException;
import pl.sda.customersafterkurs.service.exception.VatAlreadyExistsException;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    @Autowired
    private CustomerRepository repository;


    @Test
    void shouldRegisterCompany() {
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
    void shouldNotRegisterCompanyIfEmailExists() {
        //Given
        repository.saveAndFlush(new Company("dds@gmail.com", "HP", "PL999324234"));
        final var form = new RegisterCompanyForm("IBM", "PL99930223", "dds@gmail.com");

        //When & Then
        assertThrows(EmailAlreadyExistsException.class, () -> service.registerCompany(form));

    }

    @Test
    void shouldNotRegisterCompanyIfVatExists() {
        //Given
        repository.saveAndFlush(new Company("dds@gmail.com", "HP", "PL99930223"));
        final var form = new RegisterCompanyForm("IBM", "PL99930223", "daaas@gmail.com");

        //When & Then
        assertThrows(VatAlreadyExistsException.class, () -> service.registerCompany(form)); // labda wykonuje kod, zostaje przechwycony wyjątek i porównany z wyjątkiem wejściowym

    }

    @Test
    void shouldRegisterPerson() {
        //Given
        final var form = new RegisterPersonForm("asd@wp.pl", "Adam", "Małysz", "7904120123543");

        //When
        final var personId = service.registerPerson(form);

        //Then
        assertNotNull(personId);
        assertTrue(repository.existsById(personId.getId()));
    }

    @Test
    void shouldNotRegisterPersonIfExistsEmail(){
        //Given
        final var person = new Person("lol@wp.pl", "Adam", "Małysz", "7904120123543");
        repository.saveAndFlush(person);
        final var form = new RegisterPersonForm("lol@wp.pl", "Jan", "Kowalski", "3304120123543");

        //When & Then
        assertThrows(EmailAlreadyExistsException.class, () -> service.registerPerson(form));
    }

    @Test
    void shouldNotRegisterPersonIfExistsPesel(){
        //Given
        repository.saveAndFlush(new Person("al@wp.pl", "Adam", "Małysz", "7904120123543"));
        final var form = new RegisterPersonForm("fl@wp.pl", "Jerzy", "Romanow", "7904120123543");

        //When & Then
        assertThrows(PeselAlreadyExistsException.class, () -> service.registerPerson(form));

    }

 /**   @Test
    void shouldNotRegisterPersonIfPeselIsNotValid(){
        //Given
        final var form = new RegisterPersonForm("fl@wp.pl", "Jerzy", "Romanow", "0123543");
        //When & Then
        assertThrows(PeselNotValidateException.class, () -> service.registerPerson(form));

    }*/
}