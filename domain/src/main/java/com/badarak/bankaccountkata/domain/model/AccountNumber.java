package com.badarak.bankaccountkata.domain.model;

import java.util.Objects;

public class AccountNumber {
    private final String iban;

    public AccountNumber(String iban) {
        Objects.requireNonNull(iban);
        this.iban = iban;
    }

    public static AccountNumber fromIban(String iban){
        return new AccountNumber(iban);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountNumber that = (AccountNumber) o;
        return Objects.equals(iban, that.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban);
    }

    public String getIban() {
        return iban;
    }
}
