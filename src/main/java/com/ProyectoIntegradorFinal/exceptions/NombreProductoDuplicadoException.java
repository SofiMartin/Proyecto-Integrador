package com.ProyectoIntegradorFinal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NombreProductoDuplicadoException extends RuntimeException{
    public NombreProductoDuplicadoException(String message) {
        super(message);
    }
}
