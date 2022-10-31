package pl.sda.customersafterkurs.service.exception;

import lombok.NonNull;

public class EmailAlreadyExistsException extends BusinessServiceException {
    public EmailAlreadyExistsException(@NonNull String message) {
        super(message);
    }
}
