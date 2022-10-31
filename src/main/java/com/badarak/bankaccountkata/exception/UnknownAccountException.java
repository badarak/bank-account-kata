package com.badarak.bankaccountkata.exception;

public class UnknownAccountException extends RuntimeException{

    public UnknownAccountException(String message){
        super(message);
    }
}
