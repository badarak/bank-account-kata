package com.badarak.adapter;

import com.badarak.bankaccountkata.domain.model.Account;
import com.badarak.bankaccountkata.domain.model.AccountNumber;
import com.badarak.bankaccountkata.domain.model.Amount;
import com.badarak.bankaccountkata.domain.model.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsolePrinterServiceAdapterTest {
    private Account account;
    private final ConsolePrinterServiceAdapter consolePrinterServiceAdapter = new ConsolePrinterServiceAdapter();

    @BeforeEach
    void setUp() {
        account = new Account(new AccountNumber("FR1234567891011213"),
                Amount.amountOf(valueOf(100)),
                Statement.emptyStatement());
    }

    @Test
    void should_print_statement_to_console() {
        //Given
        Account accountToPrint = account.deposit(Amount.amountOf(valueOf(100)), now())
                .withdraw(Amount.amountOf(valueOf(200)), now())
                .deposit(Amount.amountOf(valueOf(300)), now());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));


        //When
        consolePrinterServiceAdapter.print(accountToPrint.getStatement());

        // Then
        String output = baos.toString();
        assertTrue(output.contains("type=DEPOSIT, amount=Amount{value=100}"));
        assertTrue(output.contains("type=WITHDRAWAL, amount=Amount{value=200}"));
        assertTrue(output.contains("type=DEPOSIT, amount=Amount{value=300}"));
    }
}