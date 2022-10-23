package pl.sda.customersafterkurs.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.annotations.Type;


import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@EqualsAndHashCode
public final class  Address { //final - nie można dziedziczyć

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    private String street;
    private String city;
    private String zipCode;
    private String countryCode;

    private Address(){
    }


    public Address(@NonNull String street,@NonNull String city,@NonNull String zipCode,@NonNull String countryCode) {
        this.id = UUID.randomUUID();
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.countryCode = countryCode;
    }
}
