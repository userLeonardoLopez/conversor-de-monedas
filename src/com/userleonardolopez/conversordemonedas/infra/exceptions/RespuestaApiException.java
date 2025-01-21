package com.userleonardolopez.conversordemonedas.infra.exceptions;

public class RespuestaApiException extends RuntimeException{

    public RespuestaApiException(String message) {
        super(message);
    }

}
