package pl.sda.customersafterkurs.service.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.sda.customersafterkurs.entity.CustomerType;

import java.util.List;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public abstract class CustomerDetails {

    private final CustomerType type;
    private final String email;
    private final List<AddressView> addresses;

    @Value
    @EqualsAndHashCode(callSuper = true)
    public static class PersonCustomerDetails extends CustomerDetails{

        String firstName;
        String lastName;
        String pesel;


        public PersonCustomerDetails(String email,
                                     String firstName,
                                     String lastName,
                                     String pesel,
                                     List<AddressView> addresses) {
            super(CustomerType.PERSON, email, addresses);
            this.firstName = firstName;
            this.lastName = lastName;
            this.pesel = pesel;
        }
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    public static class CompanyCustomerDetails extends CustomerDetails{

        String name;
        String vat;

        public CompanyCustomerDetails(String email,
                                      String name,
                                      String vat,
                                      List<AddressView> addresses) {
            super(CustomerType.COMPANY, email, addresses);
            this.name = name;
            this.vat = vat;
        }
    }

}
