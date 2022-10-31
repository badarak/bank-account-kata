package com.badarak.bankaccountkata.exception;

public class UnsufficientBalanceException extends IllegalStateException{
    public UnsufficientBalanceException(String message){
        super(message);
    }
}
