package com.badarak;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BankTest {

    @ParameterizedTest
    @EnumSource(value = Operation.class, names = {"TRANSFER_RECEIVED","CASH_DEPOSIT"})
    public void testDeposit(Operation operation) {
        Account account = new Account();
        account.deposit(20, operation);
        assertEquals(20, account.getBalance());
    }

    @ParameterizedTest
    @EnumSource(value = Operation.class, names = {"TRANSFER_ISSUED","CREDIT_CARTE_WITHDRAWAL","DIRECT_DEBIT"})
    public void test_withdrawal_without_overdraft(Operation operation) {
        Account account = new Account();
        account.deposit(100, Operation.TRANSFER_RECEIVED);
        account.withdrawal(10, operation);
        assertEquals(90, account.getBalance());
    }

    @Test
    public void test_withdrawal_resulting_in_overdraft() {
        Account account = new Account();
        account.deposit(10, Operation.TRANSFER_RECEIVED);

        final RuntimeException thrown =  assertThrows(RuntimeException.class,
                () -> account.withdrawal(15, Operation.CREDIT_CARTE_WITHDRAWAL));
        assertEquals("Withdrawal will result in an overdraft", thrown.getMessage());
    }

    @Test
    public void test_number_of_lines_of_statement() {
        Account account = new Account();
        account.deposit(10, Operation.TRANSFER_RECEIVED);
        account.withdrawal(5, Operation.DIRECT_DEBIT);
        assertEquals(2, account.getStatementLines().size());
    }


    @Test
    public void test_call_printStatement_shoul_not_return_error(){
        Account account = new Account();
        account.deposit(10, Operation.TRANSFER_RECEIVED);
        assertDoesNotThrow(() -> StatementPrinter.printStatement(account));
    }
}
