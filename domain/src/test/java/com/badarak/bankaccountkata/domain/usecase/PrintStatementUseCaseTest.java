package com.badarak.bankaccountkata.domain.usecase;


import com.badarak.bankaccountkata.domain.model.*;
import com.badarak.bankaccountkata.domain.repository.AccountRepository;
import com.badarak.bankaccountkata.domain.service.StatementPrinterService;
import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.valueOf;
import static org.mockito.Mockito.*;

class PrintStatementUseCaseTest {
    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final StatementPrinterService statementPrinterService = mock(StatementPrinterService.class);
    private final PrintStatementUseCase printStatementUseCase = new PrintStatementUseCase(
            accountRepository,
            statementPrinterService
    );

    @Test
    void should_print_statement() {
        //Given
        String iban = "FR1234567891011213";
        Account account = new Account(new AccountNumber(iban),
                Amount.amountOf(valueOf(100)),
                Statement.emptyStatement());
        when(accountRepository.findAccountByIban(iban)).thenReturn(account);

        //When
        printStatementUseCase.printStatement(iban);

        //Then
        verify(statementPrinterService, times(1)).print(account.getStatement());
    }
}
