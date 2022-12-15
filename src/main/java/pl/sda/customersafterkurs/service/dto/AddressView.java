package pl.sda.customersafterkurs.service.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class AddressView {

    UUID addressId;
    String street;
    String city;
    String zipCode;
    String countryCode;



}
