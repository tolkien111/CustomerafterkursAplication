package pl.sda.customersafterkurs.entity;

import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private EntityManager em;

    @Test
    void shouldSave() {
        //Given
        final var customer01 = new Person("adv@wp.pl", "Janek", "Marciniak", "0907987658758");
        final var customer02 = new Company("agrw@wp.pl", "Nowa Firma", "PL32439555");

        //When
        repository.saveAllAndFlush(List.of(customer01, customer02));

        //Then
        assertEquals(2, repository.count());
    }

    @Test
    void shouldFindPersonByLastname() {
        //Given
        final var customer01 = new Person("adv@wp.pl", "Janek", "Marciniak", "0907987658758");
        final var customer02 = new Person("aaaav@wp.pl", "Roman", "Nowak", "090000000758");
        repository.saveAllAndFlush(List.of(customer01, customer02));

        //When
        final var result = repository.findByLastName("Nowak");

        //Then
        assertEquals(customer02, result.get(0));
        assertEquals(List.of(customer02), result);


    }

    @Test
    void shouldFindPersonByFirstNameAndLastName() {
        //Given
        final var customer01 = new Person("adv@wp.pl", "Janek", "Marciniak", "0907987658758");
        final var customer02 = new Person("aaaav@wp.pl", "Roman", "Nowak", "090000000758");
        final var customer03 = new Person("aaasasav@wp.pl", "Romano", "Nowakiewicz", "0900000758");

        repository.saveAllAndFlush(List.of(customer01, customer02, customer03));

        //When
        final var result = repository
                .findByFirstNameStartingWithIgnoreCaseAndLastNameStartingWithIgnoreCase("rom", "nowak");

        //Then
        assertEquals(List.of(customer02, customer03), result);


    }

    @Test
    void shouldFindPersonByEmail() {
        //Given
        final var customer01 = new Person("adv@wP.pl", "Janek", "Marciniak", "0907987658758");
        final var customer02 = new Person("aaaav@Wp.pl", "Roman", "Nowak", "090000000758");
        final var customer03 = new Person("aaasasav@gmail.com", "Romano", "Nowakiewicz", "0900000758");
        final var customer04 = new Company("agrw@WP.PL", "Nowa Firma", "PL32439555");
        repository.saveAllAndFlush(List.of(customer01, customer02, customer03, customer04));

        //When
        final var result = repository
                .findByEmailEndingWithIgnoreCase("wp.pl");

        //Then
        assertEquals(List.of(customer01, customer02, customer04), result);


    }

    @Test
    void shouldSearchPeople() {
        //Given
        final var customer01 = new Person("adv@wp.pl", "Janek", "Marciniak", "0907987658758");
        final var customer02 = new Person("aaaav@wp.pl", "Roman", "Nowak", "090000000758");
        final var customer03 = new Person("aaasasav@wp.pl", "Romano", "Nowakiewicz", "0900000758");


        repository.saveAllAndFlush(List.of(customer01, customer02, customer03));

        //When
        final var result = repository
                .searchPeople("rom%", "nowak%");

        //Then
        assertEquals(List.of(customer02, customer03), result);

    }

    @Test
    void shouldFindCustomerInCity() {
        //Given
        final var customer01 = new Person("adv@wP.pl", "Janek", "Marciniak", "0907987658758");
        final var customer02 = new Person("aaaav@Wp.pl", "Roman", "Nowak", "090000000758");
        final var customer03 = new Person("aaasasav@gmail.com", "Romano", "Nowakiewicz", "0900000758");
        final var customer04 = new Company("agrw@WP.PL", "Nowa Firma", "PL32439555");

        customer01.addAddress(new Address("str", "Wawa", "04-222", "PL"));
        customer01.addAddress(new Address("stra", "Orzysz", "66-222", "PL"));
        customer02.addAddress(new Address("sds", "Kraków", "14-222", "PL"));
        customer03.addAddress(new Address("Jerozolimskie", "Częstochowa", "55-222", "PL"));
        customer04.addAddress(new Address("str", "Wawa", "04-222", "PL"));

        repository.saveAllAndFlush(List.of(customer01, customer02, customer03, customer04));

        //When
        final var result = repository.findCustomerInCity("Wawa");


        //Then
        assertTrue(result.containsAll(List.of(customer04, customer01))); //od trenera,poprawione, działa
    }

    @Test
    void shouldFindCompaniesInCountrySortedByName() {
        //Given
        final var customer01 = new Company("bsfw@WP.PL", "Nowa Firma", "PL3324234234555");
        final var customer02 = new Company("bfdh@WP.PL", "Stara Firma", "PL324576967839555");
        final var customer03 = new Company("werew@WP.PL", "Moja Firma", "PL32437897899555");
        final var customer04 = new Company("agrw@WP.PL", "Firma", "PL3243000009555");

        customer01.addAddress(new Address("str", "Wawa", "04-222", "PL"));
        customer01.addAddress(new Address("stra", "Orzysz", "66-222", "DE"));
        customer02.addAddress(new Address("sds", "Kraków", "14-222", "DE"));
        customer03.addAddress(new Address("Jerozolimskie", "Częstochowa", "55-222", "DE"));
        customer04.addAddress(new Address("str", "Wawa", "04-222", "PL"));

        repository.saveAllAndFlush(List.of(customer01, customer02, customer03, customer04));

        //When
        final var result = repository.findCompaniesInCountrySortedByName("DE");

        System.out.println(result);

        //Then
        assertTrue(result.containsAll(List.of(customer03, customer02, customer01))); //gdy nieposortowane ->containsAll
        assertEquals(List.of(customer03, customer01, customer02), result);//gdy mamy posortowane obiekty ->equals
    }

    @Test
    void shouldFindAllAddressesForLastName() {

        //Given
        final var customer01 = new Person("adv@wP.pl", "Janek", "Kowalski", "0907987658758");
        final var customer02 = new Person("aaaav@Wp.pl", "Roman", "Nowak", "090000000758");
        final var customer03 = new Person("aaasasav@gmail.com", "Romano", "Nowakiewicz", "0900000758");
        final var customer04 = new Person("aaasasav@gmail.com", "Romano", "Kowalski", "0900000758");

        final var address01 = new Address("str", "Wawa", "04-222", "PL");
        final var address02 = new Address("stra", "Orzysz", "66-222", "PL");
        final var address03 = new Address("sds", "Kraków", "14-222", "PL");
        final var address04 = new Address("Jerozolimskie", "Częstochowa", "55-222", "PL");
        final var address05 = new Address("str", "Wawa", "04-222", "PL");

        customer01.addAddress(address01);
        customer01.addAddress(address02);
        customer02.addAddress(address03);
        customer03.addAddress(address04);
        customer04.addAddress(address05);

        repository.saveAllAndFlush(List.of(customer01, customer02, customer03, customer04));

        //When
        final var result = repository.findAllAddressesForLastName("Kowalski");

        //Then
        assertTrue(result.containsAll(List.of(address01, address02, address05)));
    }

    @Test
    void shouldCountCustomerByCity() {
        //Given
        final var customer01 = new Person("adv@wP.pl", "Janek", "Marciniak", "0907987658758");
        final var customer02 = new Person("aaaav@Wp.pl", "Roman", "Nowak", "090000000758");
        final var customer03 = new Person("aaasasav@gmail.com", "Romano", "Nowakiewicz", "0900000758");
        final var customer04 = new Company("agrw@WP.PL", "Nowa Firma", "PL32439555");
        final var customer05 = new Company("agrw@WP.PL", "Firma", "PL3243000009555");

        customer01.addAddress(new Address("str", "Wawa", "04-222", "PL"));
        customer01.addAddress(new Address("stra", "Wyszków", "66-222", "PL"));
        customer01.addAddress(new Address("Nowa ulica", "Wawa", "67-222", "PL"));
        customer02.addAddress(new Address("sds", "Wawa", "14-222", "PL"));
        customer03.addAddress(new Address("Jerozolimskie", "Elbląg", "55-222", "PL"));
        customer04.addAddress(new Address("str", "Wawa", "04-222", "PL"));
        customer05.addAddress(new Address("aaa", "Elbląg", "82-100", "PL"));

        repository.saveAllAndFlush(List.of(customer01, customer02, customer03, customer04, customer05));

        //When
        final var result = repository.countCustomersByCity();


        //Then
        assertEquals(3, result.size());
        assertArrayEquals(new Object[]{"Elbląg", 2L}, result.get(0));
        assertArrayEquals(new Object[]{"Wawa", 3L}, result.get(1));
        assertArrayEquals(new Object[]{"Wyszków", 1L}, result.get(2));
    }

    @Test
    void shouldCountCustomerByCountryCode() {
        //Given
        final var customer01 = new Person("adv@wP.pl", "Janek", "Marciniak", "0907987658758");
        final var customer02 = new Person("aaaav@Wp.pl", "Roman", "Nowak", "090000000758");
        final var customer03 = new Person("aaasasav@gmail.com", "Romano", "Nowakiewicz", "0900000758");
        final var customer04 = new Company("agrw@WP.PL", "Nowa Firma", "PL32439555");
        final var customer05 = new Company("agrw@WP.PL", "Firma", "PL3243000009555");

        customer01.addAddress(new Address("str", "Berlin", "04-222", "DE"));
        customer01.addAddress(new Address("stra", "New Jork", "66-222", "DE"));
        customer01.addAddress(new Address("Nowa ", "Berlin", "67-222", "DE"));
        customer02.addAddress(new Address("sds", "Berlin", "14-222", "DE"));
        customer03.addAddress(new Address("Jer", "Elbląg", "55-222", "PL"));
        customer04.addAddress(new Address("str", "Berlin", "04-222", "DE"));
        customer05.addAddress(new Address("aaa", "Elbląg", "82-100", "PL"));

        repository.saveAllAndFlush(List.of(customer01, customer02, customer03, customer04, customer05));

        //When
        final var result = repository.countCustomersByCountryCode();
        final var row01 = result.get(0);
        final var row02 = result.get(1);
        final var row03 = result.get(2);

        //Then
        assertEquals(3, result.size());
        assertEquals("DE", row01.getCountryCode());
        assertEquals(3, row01.getCount());
        assertEquals("PL", row02.getCountryCode());
        assertEquals(2, row02.getCount());
        assertEquals("US", row03.getCountryCode());
        assertEquals(1, row03.getCount());

    }

    @Test
    void shouldFindCompaniesWithZipCode() {
        //Given
        final var customer01 = new Company("bsfw@WP.PL", "Nowa Firma", "PL3324234234555");
        final var customer02 = new Company("bfdh@WP.PL", "Stara Firma", "PL324576967839555");
        final var customer03 = new Company("werew@WP.PL", "Moja Firma", "PL32437897899555");
        final var customer04 = new Company("agrw@WP.PL", "Firma", "PL3243000009555");

        customer01.addAddress(new Address("str", "Wawa", "14-222", "PL"));
        customer01.addAddress(new Address("stra", "Orzysz", "66-222", "DE"));
        customer02.addAddress(new Address("sds", "Kraków", "14-262", "PL"));
        customer03.addAddress(new Address("Jero", "Częstochowa", "55-222", "DE"));
        customer04.addAddress(new Address("str", "Wawa", "14-432", "PL"));

        repository.saveAllAndFlush(List.of(customer01, customer02, customer03, customer04));

        //When
        final var result = repository.findCompaniesWithZipCode("14%");


        System.out.println(result);
        //Then
        assertTrue(result.containsAll(List.of(
                new CompanyZipCodeView("Nowa Firma", "PL3324234234555", "14-222"),
                new CompanyZipCodeView("Stara Firma", "PL324576967839555", "14-262"),
                new CompanyZipCodeView("Firma", "PL3243000009555", "14-432")
        )));
    }

    @Test
    void shouldFindPersonViewByEmail() {
        //Given
        final var customer01 = new Person("adv@wP.pl", "Janek", "Kowalski", "0907987658758");
        final var customer02 = new Person("abaav@Wp.pl", "Roman", "Nowak", "090000000758");
        final var customer03 = new Person("aaasasav@gmail.com", "Romano", "Nowakiewicz", "0900000758");
        final var customer04 = new Person("aaasasav@gmail.com", "Romano", "Kowalski", "0900000758");

        repository.saveAllAndFlush(List.of(customer01, customer02, customer03, customer04));

        //When
        final var result = repository.findPersonViewByEmail("%.com");

        //Then
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(
                new PersonView(customer03.getId(), customer03.getEmail(), customer03.getPesel()),
                new PersonView(customer04.getId(), customer04.getEmail(), customer04.getPesel())
        )));
    }

    //UPDATE W DB
    @Test
    void shouldUpdateCountryCodeForCity(){
        //Given
        final var customer01 = new Company("bsfw@WP.PL", "Nowa Firma", "PL3324234234555");
        final var customer02 = new Company("bfdh@WP.PL", "Stara Firma", "PL324576967839555");
        final var customer03 = new Company("werew@WP.PL", "Moja Firma", "PL32437897899555");
        final var customer04 = new Company("agrw@WP.PL", "Firma", "PL3243000009555");

        customer01.addAddress(new Address("str", "Wawa", "14-222", "DE"));
        customer02.addAddress(new Address("sds", "Kraków", "14-262", "PL"));
        customer03.addAddress(new Address("Jero", "Częstochowa", "55-222", "PL"));
        customer04.addAddress(new Address("str", "Wawa", "14-432", "DE"));

        repository.saveAllAndFlush(List.of(customer01, customer02, customer03, customer04));

        //When
        final int result = repository.updateCountryCodeForCity("Wawa", "PL");
        em.clear();



        //Then
        assertEquals(2, result);
        assertEquals(0, repository.countCityWithCountryCode("Wawa", "DE"));
        final var addresses = repository.findByCity("Wawa");
        assertEquals(2, addresses.size());
        addresses.forEach(address -> assertEquals("PL", address.getCountryCode()));
    }

    @Test
    void shouldDeleteAllAddressesWithZipCode(){
        //Given
        final var customer01 = new Company("bsfw@WP.PL", "Nowa Firma", "PL3324234234555");
        final var customer02 = new Company("bfdh@WP.PL", "Stara Firma", "PL324576967839555");
        final var customer03 = new Company("werew@WP.PL", "Moja Firma", "PL32437897899555");
        final var customer04 = new Company("agrw@WP.PL", "Firma", "PL3243000009555");

        customer01.addAddress(new Address("str", "Wawa", "14-222", "DE"));
        customer02.addAddress(new Address("sds", "Kraków", "14-322", "PL"));
        customer03.addAddress(new Address("Jero", "Częstochowa", "55-222", "PL"));
        customer04.addAddress(new Address("str", "Wawa", "14-432", "DE"));

        repository.saveAllAndFlush(List.of(customer01, customer02, customer03, customer04));

        //When
        final var result = repository.deleteAllAddressesWithZipCode("14-2%");

        //em.clear();

        //Then
        assertEquals(1,result);
        assertEquals(3, repository.countAllAddresses());
    }


}

