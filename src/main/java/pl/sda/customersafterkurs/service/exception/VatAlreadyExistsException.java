package pl.sda.customersafterkurs.service.exception;

import lombok.NonNull;

public class VatAlreadyExistsException extends BusinessServiceException {
    public VatAlreadyExistsException(@NonNull String message) {
        super(message);
    }
}
