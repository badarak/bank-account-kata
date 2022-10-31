package com.badarak.bankaccountkata.repository;

import com.badarak.bankaccountkata.business.Account;

public interface AccountRepository {
    Account findAccountByIban(String iban);
    Account save(Account account);
}
