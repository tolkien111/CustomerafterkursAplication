package pl.sda.customersafterkurs.entity;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


class CustomerTest extends EntityTest {

    @Test
    void shouldSaveCompany(){
        //Given
        final var company = new Company("aaaa.@wp.pl", "ACompany","PL9200200022");

        //When
        persist(company);

        //Then
        final var readCompany = em.find(Company.class, company.getId());
        assertEquals(company, readCompany);
    }
    @Test
    void shouldSavePerson(){
        //Given
        final var person = new Person("abs@wp.pl", "Jan", "Nowak", "99021211987");

        //When
        persist(person);

        //Then
        final var readPerson = em.find(Person.class, person.getId());
        assertEquals(person, readPerson);
}


}