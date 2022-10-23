package pl.sda.customersafterkurs.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table (name = "customers")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "customer_type")
@NoArgsConstructor (access = AccessLevel.PROTECTED) // protected żeby podklasy mogły się dostać
@Getter
public abstract class Customer {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    private String email;

    @OneToMany
    @JoinColumn (name = "customer_id")
    private List<Address> addresses;

    protected Customer(String email) { // też protected aby miały dostęp tylko klasy dziedziczące
        this.id = UUID.randomUUID();
        this.email = email;
        addresses = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id) && email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
