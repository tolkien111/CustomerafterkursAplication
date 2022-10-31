package pl.sda.customersafterkurs.service.dto;

import lombok.*;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public record RegisteredCustomerId(@NonNull UUID id) {

    //zmieniony na rekord
}
