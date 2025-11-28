package com.badarak.adapter;


import com.badarak.bankaccountkata.domain.exception.UnknownAccountException;
import com.badarak.bankaccountkata.domain.model.Account;
import com.badarak.bankaccountkata.domain.model.AccountNumber;
import com.badarak.bankaccountkata.domain.repository.AccountRepository;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class InMemoryAccountRepositoryAdapter implements AccountRepository {
    private final Map<AccountNumber, Account> accountMap ;

    public InMemoryAccountRepositoryAdapter() {
        this.accountMap = new HashMap<>();
    }

    @Override
    public Account findAccountByIban(String iban) {
        return ofNullable(accountMap.get(AccountNumber.fromIban(iban)))
                .orElseThrow(() -> new UnknownAccountException("No account exist with this Iban."));
    }

    @Override
    public Account save(Account account) {
        accountMap.put(account.getAccountId(), account);
        return account;
    }
}
