package com.badarak.bankaccountkata.business;

import com.badarak.bankaccountkata.exception.UnsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static com.badarak.bankaccountkata.business.Amount.amountOf;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.*;


class AccountTest {
    private Account account;

    @BeforeEach
    public void setUp(){
        account = new Account(new AccountNumber("FR123"),
                amountOf(valueOf(100)),
                Statement.emptyStatement());
    }

    @Test
    void shouldReturnTwoHundredAfterDeposit(){
        Account accountAfterDeposit = account.deposit(amountOf(valueOf(100)), LocalDateTime.now());
        assertEquals( valueOf(200), accountAfterDeposit.getBalance().getValue());
    }

    @Test
    void shouldReturnZeroAfterWithdrawalOfRemainingHundred(){
        Account accountAfterWithDrawal = account.withdraw(amountOf(valueOf(100)), LocalDateTime.now());
        assertEquals(valueOf(0), accountAfterWithDrawal.getBalance().getValue());
    }

    @Test
    void shouldThrowUnsufficientBalanceExceptionWhenWithrawalResultingInOverdraft(){
        LocalDateTime now = LocalDateTime.now();
        Amount amount = amountOf(valueOf(1000));
        assertThrows(UnsufficientBalanceException.class, () -> account.withdraw(amount, now));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Insufficient balance for this withdrawal."})
    void shouldThrowUnsufficientBalanceExceptionWhenWithrawalResultingInOverdraft(String msg){
        LocalDateTime now = LocalDateTime.now();
        Amount amount = amountOf(valueOf(500));
        UnsufficientBalanceException thrown = assertThrows(UnsufficientBalanceException.class,
                () -> account.withdraw(amount, now));
        assertEquals(msg, thrown.getMessage());
    }

    @Test
    void shouldReturnAHundredAfterDepositAndWithrdrawalOfAHundred(){
        Account accountAfterOperation = account.deposit(amountOf(valueOf(100)), LocalDateTime.now())
                .withdraw(amountOf(valueOf(100)), LocalDateTime.now());

        assertEquals(valueOf(100),accountAfterOperation.getBalance().getValue());
    }

    @Test
    void shouldAddANewTransactionToStatementWhenWithDrawal() {
        LocalDateTime date = LocalDateTime.of(2020, 5, 24, 14, 0);
        Account accountAfterWithDrawal = account.withdraw(amountOf(valueOf(100)), date);

        assertTrue(accountAfterWithDrawal.getStatement().getLastTransaction().isPresent());
        assertEquals(accountAfterWithDrawal.getBalance(),
                accountAfterWithDrawal.getStatement().getLastTransaction().map(Transaction::getNewBalance).get());
        assertEquals(TransactionType.WITHDRAWAL,
                accountAfterWithDrawal.getStatement().getLastTransaction().map(Transaction::getType).get());
        assertEquals(amountOf(valueOf(100)),
                accountAfterWithDrawal.getStatement().getLastTransaction().map(Transaction::getAmount).get());
        assertEquals(date,
                accountAfterWithDrawal.getStatement().getLastTransaction().map(Transaction::getDate).get());
    }

    @Test
    void shouldAddANewTransactionToStatementWhenDeposit() {
        LocalDateTime date = LocalDateTime.of(2022, 11, 6, 12,5);
        Account accountAfterWithDeposit = account.deposit(amountOf(valueOf(100)), date);

        assertTrue(accountAfterWithDeposit.getStatement().getLastTransaction().isPresent());
        assertEquals(accountAfterWithDeposit.getBalance(),
                accountAfterWithDeposit.getStatement().getLastTransaction().map(Transaction::getNewBalance).get());
        assertEquals(TransactionType.DEPOSIT,
                accountAfterWithDeposit.getStatement().getLastTransaction().map(Transaction::getType).get());
        assertEquals(amountOf(valueOf(100)),
                accountAfterWithDeposit.getStatement().getLastTransaction().map(Transaction::getAmount).get());

        assertEquals(date,
                accountAfterWithDeposit.getStatement().getLastTransaction().map(Transaction::getDate).get());
    }

}
