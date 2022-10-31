package com.badarak.bankaccountkata.business;

import com.badarak.bankaccountkata.exception.UnsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.badarak.bankaccountkata.business.Amount.amountOf;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.*;


public class AccountTest {
    private Account account;

    @BeforeEach
    public void setUp(){
        account = new Account(new AccountNumber("FR123"),
                amountOf(valueOf(100)),
                Statement.emptyStatement());
    }

    @Test
    public void shouldReturnTwoHundredAfterDeposit(){
        Account accountAfterDeposit = account.deposit(amountOf(valueOf(100)));
        assertEquals( valueOf(200), accountAfterDeposit.getBalance().getValue());
    }

    @Test
    public void shouldReturnZeroAfterWithdrawalOfRemainingHundred(){
        Account accountAfterWithDrawal = account.withdraw(amountOf(valueOf(100)));
        assertEquals(valueOf(0), accountAfterWithDrawal.getBalance().getValue());
    }

    @Test
    public void shouldThrowUnsufficientBalanceExceptionWhenWithrawalResultingInOverdraft(){
        assertThrows(UnsufficientBalanceException.class, () -> account.withdraw(amountOf(valueOf(1000))));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Insufficient balance for this withdrawal."})
    public void shouldThrowUnsufficientBalanceExceptionWhenWithrawalResultingInOverdraft(String msg){
        UnsufficientBalanceException thrown = assertThrows(UnsufficientBalanceException.class,
                () -> account.withdraw(amountOf(valueOf(500))));
        assertEquals(msg, thrown.getMessage());
    }

    @Test
    public void shouldReturnAHundredAfterDepositAndWithrdrawalOfAHundred(){
        Account accountAfterOperation = account.deposit(amountOf(valueOf(100)))
                .withdraw(amountOf(valueOf(100)));

        assertEquals(valueOf(100),accountAfterOperation.getBalance().getValue());
    }

    @Test
    public void shouldAddANewTransactionToStatementWhenWithDrawal() {
        Account accountAfterWithDrawal = account.withdraw(amountOf(valueOf(100)));

        assertTrue(accountAfterWithDrawal.getStatement().getLastTransaction().isPresent());
        assertEquals(accountAfterWithDrawal.getBalance(),
                accountAfterWithDrawal.getStatement().getLastTransaction().map(Transaction::getNewBalance).get());
        assertEquals(TransactionType.WITHDRAWAL,
                accountAfterWithDrawal.getStatement().getLastTransaction().map(Transaction::getType).get());
        assertEquals(amountOf(valueOf(100)),
                accountAfterWithDrawal.getStatement().getLastTransaction().map(Transaction::getAmount).get());
    }

    @Test
    public void shouldAddANewTransactionToStatementWhenDeposit() {
        Account accountAfterWithDeposit = account.deposit(amountOf(valueOf(100)));

        assertTrue(accountAfterWithDeposit.getStatement().getLastTransaction().isPresent());
        assertEquals(accountAfterWithDeposit.getBalance(),
                accountAfterWithDeposit.getStatement().getLastTransaction().map(Transaction::getNewBalance).get());
        assertEquals(TransactionType.DEPOSIT,
                accountAfterWithDeposit.getStatement().getLastTransaction().map(Transaction::getType).get());
        assertEquals(amountOf(valueOf(100)),
                accountAfterWithDeposit.getStatement().getLastTransaction().map(Transaction::getAmount).get());
    }
}
