package com.badarak;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class AccountTest {


    @Test
    public void testDeposit() {
        Account account1 = new Account(10);
        Account account2 = new Account();

        account1.deposit(20);
        account2.deposit(20);

        assertEquals(30, account1.getBalance());
        assertEquals(20, account2.getBalance());
    }


    @Test
    public void test_withdrawal_without_overdraft() {
        Account account = new Account(10);
        account.withdrawal(5);
        assertEquals(5, account.getBalance());
    }

    @Test
    public void test_withdrawal_resulting_in_overdraft() {
        Account account = new Account(10);
        assertThrows(RuntimeException.class, () -> account.withdrawal(15));
    }
}
