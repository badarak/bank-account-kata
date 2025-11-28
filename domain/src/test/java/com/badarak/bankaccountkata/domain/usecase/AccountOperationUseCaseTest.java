package com.badarak.bankaccountkata.domain.usecase;

import com.badarak.bankaccountkata.domain.model.Account;
import com.badarak.bankaccountkata.domain.model.AccountNumber;
import com.badarak.bankaccountkata.domain.model.Amount;
import com.badarak.bankaccountkata.domain.model.Statement;
import com.badarak.bankaccountkata.domain.exception.UnknownAccountException;
import com.badarak.bankaccountkata.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AccountOperationUseCaseTest {
    public static final String IBAN = "FR1234567891011213";
    public static final int INITIAL_BALANCE_AMOUNT = 100;
    private final AccountRepository repository = mock(AccountRepository.class);
    private final AccountOperationUseCase accountOperationUseCase = new AccountOperationUseCase(repository);
    private Account account;


    @BeforeEach
    void setUp() {
        account = new Account(new AccountNumber(IBAN),
                Amount.amountOf(valueOf(INITIAL_BALANCE_AMOUNT)),
                Statement.emptyStatement());
    }

    @Test
    void should_deposit_a_hundred_on_account_using_its_iban() {
        //Given
        when(repository.findAccountByIban(IBAN)).thenReturn(account);

        //When
        accountOperationUseCase.deposit(IBAN, valueOf(100));

        //Then
        ArgumentCaptor<Account> accountNumberArgumentCaptor = ArgumentCaptor.forClass(Account.class);

        verify(repository, times(1)).save(accountNumberArgumentCaptor.capture());
        assertEquals(IBAN, accountNumberArgumentCaptor.getValue().getAccountId().getIban());
        assertEquals(BigDecimal.valueOf(200), accountNumberArgumentCaptor.getValue().getBalance().getValue());
    }

    @Test
    void should_withdraw_a_hundred_from_account_using_its_iban() {
        //Given
       when(repository.findAccountByIban(IBAN)).thenReturn(account);

        //When
       accountOperationUseCase.withdraw(IBAN, valueOf(100));

       //Then
        ArgumentCaptor<Account> accountNumberArgumentCaptor = ArgumentCaptor.forClass(Account.class);

        verify(repository, times(1)).save(accountNumberArgumentCaptor.capture());
        assertEquals(IBAN, accountNumberArgumentCaptor.getValue().getAccountId().getIban());
        assertEquals(valueOf(0), accountNumberArgumentCaptor.getValue().getBalance().getValue());
    }

    @Test
    void should_throw_UnknownAccountException_when_deposit_on_unknown_iban() {
        //Given
        String unknownIban = "UNKNOWN_IBAN";
        when(repository.findAccountByIban(unknownIban)).thenThrow(UnknownAccountException.class);

        //When & Then
        BigDecimal amount = valueOf(100);
        assertThrows(UnknownAccountException.class, () -> accountOperationUseCase.deposit(unknownIban, amount));
    }
}
