package com.badarak.bankaccountkata.features;

import com.badarak.bankaccountkata.adapter.InMemoryAccountRepositoryAdapter;
import com.badarak.bankaccountkata.business.Account;
import com.badarak.bankaccountkata.business.AccountNumber;
import com.badarak.bankaccountkata.business.Amount;
import com.badarak.bankaccountkata.business.Statement;
import com.badarak.bankaccountkata.exception.UnknownAccountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountOperationFeatureTest {
    public static final String IBAN = "FR1234567891011213";
    public static final String UNKNOWN_IBAN = "FRXXXXXXXXXXX";
    public static final int INITIAL_BALANCE_AMOUNT = 100;
    private AccountOperationFeature accountOperationFeature;


    @BeforeEach
    public void setUp(){
        InMemoryAccountRepositoryAdapter inMemoryRepository = new InMemoryAccountRepositoryAdapter();
        Account account = new Account(new AccountNumber(IBAN),
                Amount.amountOf(valueOf(INITIAL_BALANCE_AMOUNT)),
                Statement.emptyStatement());
        inMemoryRepository.save(account);
        accountOperationFeature = new AccountOperationFeature(inMemoryRepository);
    }

    @Test
    void shouldDepositAHundredOnAccountUsingItsIban() {
        Account accountAfterDeposit = accountOperationFeature.deposit(IBAN, valueOf(100));
        assertEquals( valueOf(200), accountAfterDeposit.getBalance().getValue());
    }

    @Test
    void shouldWithdrawAHundredFromAccountUsingItsIban() {
        Account accountAfterWithdrawal = accountOperationFeature.withdraw(IBAN, valueOf(100));
        assertEquals(valueOf(0), accountAfterWithdrawal.getBalance().getValue());
    }

    @Test
    public void shouldThrowUnknownAccountExceptionWhenWhenDepositOnUnkownIban(){
        assertThrows(UnknownAccountException.class, ()-> accountOperationFeature.deposit(UNKNOWN_IBAN, valueOf(100)));
    }
}
