package pl.sda.customersafterkurs.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.sda.customersafterkurs.entity.Address;
import pl.sda.customersafterkurs.entity.CustomerRepository;
import pl.sda.customersafterkurs.entity.Person;
import pl.sda.customersafterkurs.service.dto.AddAddressForm;
import pl.sda.customersafterkurs.service.dto.CreatedAddress;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class CustomerAddressServiceTest {

    @Autowired
    private CustomerAddressService service;

    @Autowired
    private CustomerRepository repository;

    @MockBean
    private ReverseGeocoding reverseGeocoding;

    @Test
    void shouldAddAddressToCustomer() {
        //Given
        final var person = new Person("abs@wp.pl", "Jan", "Nowak", "99021211987");
        repository.save(person);
        var address = new Address("str", "Wawa", "01-200", "PL");
        when(reverseGeocoding.reverse(anyDouble(), anyDouble()))
                .thenReturn(address);

        final var form = new AddAddressForm(person.getId(), 52.242839, 20.9795852);


        //When
        final var createdAddress = service.addAddress(form);

        //Then
        assertNotNull(createdAddress);
        assertEquals(new CreatedAddress(
                person.getId(),
                address.getId(),
                "str",
                "Wawa",
                "01-200",
                "PL"), createdAddress);
    }


}