package com.bank.bank_api.exception;

/* Para:
    cliente no existe
    cuenta no existe
*/

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}