package com.badarak;

public class BankApp {

    public static void main(String[] args) {
        Account account = new Account();
        account.deposit(100, Operation.TRANSFER_RECEIVED);
        account.withdrawal(20, Operation.DIRECT_DEBIT);

        StatementPrinter.printStatement(account);
    }
}
