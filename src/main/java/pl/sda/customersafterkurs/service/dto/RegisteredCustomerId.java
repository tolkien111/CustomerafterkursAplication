package pl.sda.customersafterkurs.service.dto;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class RegisteredCustomerId {

    @NonNull
    UUID id;
}
