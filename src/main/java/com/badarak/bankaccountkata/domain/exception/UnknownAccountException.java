package com.badarak.bankaccountkata.domain.exception;

public class UnknownAccountException extends RuntimeException{

    public UnknownAccountException(String message){
        super(message);
    }
}
