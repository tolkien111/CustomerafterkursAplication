package pl.sda.customersafterkurs.service;

import pl.sda.customersafterkurs.entity.Address;

interface ReverseGeocoding {

    class ReverseGeoCodingException extends RuntimeException{

        public ReverseGeoCodingException(String message) {
            super(message);
        }

        public ReverseGeoCodingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    Address reverse(double latitude, double longitude);
}
