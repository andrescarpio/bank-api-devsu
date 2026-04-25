package com.bank.bank_api.exception;

/* Para reglas de negocio:
    saldo insuficiente
    límite excedido
*/
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}