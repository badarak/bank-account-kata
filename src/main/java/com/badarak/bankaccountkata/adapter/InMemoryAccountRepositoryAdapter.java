package com.badarak.bankaccountkata.adapter;

import com.badarak.bankaccountkata.business.Account;
import com.badarak.bankaccountkata.business.AccountNumber;
import com.badarak.bankaccountkata.repository.AccountRepository;
import com.badarak.bankaccountkata.exception.UnknownAccountException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAccountRepositoryAdapter implements AccountRepository {
    private final Map<AccountNumber, Account> accountMap ;

    public InMemoryAccountRepositoryAdapter() {
        this.accountMap = new HashMap<>();
    }

    @Override
    public Account findAccountByIban(String iban) {
        return Optional.ofNullable(accountMap.get(AccountNumber.fromIban(iban)))
                .orElseThrow(() -> new UnknownAccountException("No account exist with this Iban."));
    }

    @Override
    public Account save(Account account) {
        accountMap.put(account.getAccountId(), account);
        return account;
    }
}
