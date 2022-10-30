package com.badarak;

public class StatementPrinter {

    public static void printStatement(Account account) {
        account.getStatementLines()
                .forEach(System.out::println);
    }
}
