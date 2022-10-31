package com.badarak.bankaccountkata.features;

import com.badarak.bankaccountkata.business.Account;
import com.badarak.bankaccountkata.business.Amount;
import com.badarak.bankaccountkata.repository.AccountRepository;

import java.math.BigDecimal;

public class AccountOperationFeature {
    private final AccountRepository accountRepository;

    public AccountOperationFeature(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account deposit(String iban, BigDecimal amount){
        Account account = accountRepository.findAccountByIban(iban);
        return  accountRepository.save(account.deposit(Amount.amountOf(amount)));
    }

    public Account withdraw(String iban, BigDecimal amount){
        Account account = accountRepository.findAccountByIban(iban);
        return  accountRepository.save(account.withdraw(Amount.amountOf(amount)));
    }
}
