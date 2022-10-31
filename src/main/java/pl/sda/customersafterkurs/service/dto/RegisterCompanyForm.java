package pl.sda.customersafterkurs.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;


@Getter
@EqualsAndHashCode
@ToString
public record RegisterCompanyForm(@NonNull String name, @NonNull String vat,
                                  @NonNull String email) {
    //zmieniony na rekord

}
