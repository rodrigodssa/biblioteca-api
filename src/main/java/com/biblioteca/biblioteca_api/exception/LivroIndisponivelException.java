package com.biblioteca.biblioteca_api.exception;


public class LivroIndisponivelException extends RuntimeException {

    public LivroIndisponivelException(String message) {
        super(message);
    }
}