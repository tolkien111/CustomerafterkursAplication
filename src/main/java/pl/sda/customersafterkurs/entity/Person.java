package pl.sda.customersafterkurs.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pl.sda.customersafterkurs.service.dto.AddressView;
import pl.sda.customersafterkurs.service.dto.CustomerDetails;
import pl.sda.customersafterkurs.service.dto.RegisterPersonForm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Entity
@DiscriminatorValue("PERSON")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class Person extends Customer {

    private String firstName;
    private String lastName;
    private String pesel;

    public Person(String email, @NonNull String firstName, @NonNull String lastName, @NonNull String pesel) {
        super(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }

    public static Person createWith(RegisterPersonForm form) {
        return new Person(form.getEmail(),
                form.getFirstName(),
                form.getLastName(),
                form.getPesel());
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public CustomerDetails mapToDetails() {
        return new CustomerDetails.PersonCustomerDetails(getEmail(),firstName, lastName,pesel, getAddresses().stream()
                .map(Address::toView)
                .collect(toList()));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Person person = (Person) o;
        return firstName.equals(person.firstName) && lastName.equals(person.lastName) && pesel.equals(person.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, pesel);
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' +
                '}';
    }
}

