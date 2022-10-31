package pl.sda.customersafterkurs.service.dto;

import lombok.*;


@Value
public class RegisterCompanyForm {

    @NonNull
    String name;
    @NonNull
    String vat;
    @NonNull
    String email;
}
