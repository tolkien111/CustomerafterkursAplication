package pl.sda.customersafterkurs.service.exception;

import lombok.NonNull;

public class CustomerNotExistsException extends BusinessServiceException {
    public CustomerNotExistsException(@NonNull String message) {
        super(message);
    }
}
