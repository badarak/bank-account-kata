package com.badarak.bankaccountkata.business;

import java.math.BigDecimal;
import java.util.Objects;

public class Amount {
    private final BigDecimal value;

    public Amount(BigDecimal value) {
        this.value = value;
    }

    public Amount add(Amount toAdd){
        return amountOf(this.value.add(toAdd.value));
    }

    public Amount minus(Amount toSubtract){
        return amountOf(this.value.subtract( toSubtract.value));
    }

    public boolean isGreaterThan(Amount amount){
        return value.compareTo(amount.value) > 0;
    }

    public static Amount amountOf(BigDecimal value) {
        return new Amount(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount = (Amount) o;
        return Objects.equals(value, amount.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Amount{" +
                "value=" + value +
                '}';
    }
}
