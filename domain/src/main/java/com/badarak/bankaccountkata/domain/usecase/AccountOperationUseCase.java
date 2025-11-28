package com.badarak.bankaccountkata.domain.usecase;



import com.badarak.bankaccountkata.domain.model.Account;
import com.badarak.bankaccountkata.domain.model.Amount;
import com.badarak.bankaccountkata.domain.repository.AccountRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountOperationUseCase {
    private final AccountRepository accountRepository;

    public AccountOperationUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account deposit(String iban, BigDecimal amount){
        Account account = accountRepository.findAccountByIban(iban);
        return  accountRepository.save(account.deposit(Amount.amountOf(amount), LocalDateTime.now()));
    }

    public Account withdraw(String iban, BigDecimal amount){
        Account account = accountRepository.findAccountByIban(iban);
        return  accountRepository.save(account.withdraw(Amount.amountOf(amount), LocalDateTime.now()));
    }
}
