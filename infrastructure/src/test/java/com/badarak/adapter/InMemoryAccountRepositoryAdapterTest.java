package com.badarak.adapter;

import com.badarak.bankaccountkata.domain.exception.UnknownAccountException;
import com.badarak.bankaccountkata.domain.model.Account;
import com.badarak.bankaccountkata.domain.model.Amount;
import org.junit.jupiter.api.Test;

import static com.badarak.bankaccountkata.domain.model.AccountNumber.fromIban;
import static com.badarak.bankaccountkata.domain.model.Statement.emptyStatement;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryAccountRepositoryAdapterTest {
    private final InMemoryAccountRepositoryAdapter repositoryAdapter = new InMemoryAccountRepositoryAdapter();


    @Test
    void should_throw_UnknownAccountException_when_account_unknown() {
        assertThrows(UnknownAccountException.class, () -> repositoryAdapter.findAccountByIban("UNKNOWN"));
    }

    @Test
    void should_return_account() {
        //Given
        String iban = "123";
        Account account = new Account(fromIban(iban), Amount.amountOf(valueOf(0)), emptyStatement());
        repositoryAdapter.save(account);

        //When
        Account actual = repositoryAdapter.findAccountByIban(iban);

        //Then
        assertEquals(account, actual);
    }

    @Test
    void should_save_account() {
        //Given
        String iban = "123";
        Account account = new Account(fromIban(iban), Amount.amountOf(valueOf(0)), emptyStatement());

        //When
        repositoryAdapter.save(account);

        //Then
        assertEquals(account, repositoryAdapter.findAccountByIban(iban));
    }
}