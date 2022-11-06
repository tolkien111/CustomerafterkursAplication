package pl.sda.customersafterkurs.service.exception;

import com.sun.istack.NotNull;

public class PeselAlreadyExistsException extends BusinessServiceException {
    public PeselAlreadyExistsException(@NotNull String message) {
        super(message);
    }
}
