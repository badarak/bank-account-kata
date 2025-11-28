package com.badarak.bankaccountkata.domain.model;

import com.badarak.bankaccountkata.domain.exception.UnsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static com.badarak.bankaccountkata.domain.model.Amount.amountOf;
import static com.badarak.bankaccountkata.domain.model.Statement.emptyStatement;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.*;


class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(new AccountNumber("FR123"),
                amountOf(valueOf(100)),
                emptyStatement());
    }

    @Test
    void shoul_return_two_hundred_after_deposit() {
        Account accountAfterDeposit = account.deposit(amountOf(valueOf(100)), LocalDateTime.now());
        assertEquals(valueOf(200), accountAfterDeposit.getBalance().getValue());
    }

    @Test
    void should_return_zero_after_withdrawal_of_remaining_hundred() {
        Account accountAfterWithdrawal = account.withdraw(amountOf(valueOf(100)), LocalDateTime.now());
        assertEquals(valueOf(0), accountAfterWithdrawal.getBalance().getValue());
    }

    @Test
    void should_throw_UnsufficientBalanceException_when_withdrawal_resulting_in_overdraft() {
        LocalDateTime now = LocalDateTime.now();
        Amount amount = amountOf(valueOf(1000));
        assertThrows(UnsufficientBalanceException.class, () -> account.withdraw(amount, now));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Insufficient balance for this withdrawal."})
    void should_throw_UnsufficientBalanceException_when_withdrawal_resulting_in_overdraft(String msg) {
        LocalDateTime now = LocalDateTime.now();
        Amount amount = amountOf(valueOf(500));
        UnsufficientBalanceException thrown = assertThrows(UnsufficientBalanceException.class,
                () -> account.withdraw(amount, now));
        assertEquals(msg, thrown.getMessage());
    }

    @Test
    void should_return_a_hundred_after_deposit_and_withdrawal_of_a_hundred() {
        Account accountAfterOperation = account.deposit(amountOf(valueOf(100)), LocalDateTime.now())
                .withdraw(amountOf(valueOf(100)), LocalDateTime.now());

        assertEquals(valueOf(100), accountAfterOperation.getBalance().getValue());
    }

    @Test
    void should_add_a_new_transaction_to_statement_when_withdrawal() {
        LocalDateTime date = LocalDateTime.of(2020, 5, 24, 14, 0);
        Account accountAfterWithdrawal = account.withdraw(amountOf(valueOf(100)), date);

        assertTrue(accountAfterWithdrawal.getStatement().getLastTransaction().isPresent());
        assertEquals(accountAfterWithdrawal.getBalance(),
                accountAfterWithdrawal.getStatement().getLastTransaction().map(Transaction::getNewBalance).get());
        assertEquals(TransactionType.WITHDRAWAL,
                accountAfterWithdrawal.getStatement().getLastTransaction().map(Transaction::getType).get());
        assertEquals(amountOf(valueOf(100)),
                accountAfterWithdrawal.getStatement().getLastTransaction().map(Transaction::getAmount).get());
        assertEquals(date,
                accountAfterWithdrawal.getStatement().getLastTransaction().map(Transaction::getDate).get());
    }

    @Test
    void should_add_a_new_transaction_to_statement_when_deposit() {
        LocalDateTime date = LocalDateTime.of(2022, 11, 6, 12, 5);
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
