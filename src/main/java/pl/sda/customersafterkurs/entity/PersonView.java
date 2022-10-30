package pl.sda.customersafterkurs.entity;


import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Immutable
@Subselect("SELECT p.id, p.email, p.pesel FROM customers p WHERE customer_type = 'PERSON'")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
public class PersonView {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    private String email;
    private String pesel;
}
