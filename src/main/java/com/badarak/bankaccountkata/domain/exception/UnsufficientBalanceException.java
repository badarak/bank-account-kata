package com.badarak.bankaccountkata.domain.exception;

public class UnsufficientBalanceException extends IllegalStateException{
    public UnsufficientBalanceException(String message){
        super(message);
    }
}
