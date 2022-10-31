package pl.sda.customersafterkurs.service.dto;

import lombok.*;

import java.util.UUID;

@Value
public class RegisteredCustomerId {

    @NonNull
    UUID id;
}
