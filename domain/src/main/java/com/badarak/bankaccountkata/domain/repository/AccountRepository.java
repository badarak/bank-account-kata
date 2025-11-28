package com.badarak.bankaccountkata.domain.repository;


import com.badarak.bankaccountkata.domain.model.Account;

public interface AccountRepository {
    Account findAccountByIban(String iban);
    Account save(Account account);
}
