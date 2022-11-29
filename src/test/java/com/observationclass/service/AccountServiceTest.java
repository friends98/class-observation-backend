package com.observationclass.service;

import com.observationclass.entity.Account;
import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)

class AccountServiceTest {

    @MockBean
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void testGetAccountByEmail() {
        String email="quang41096@fpt.edu.vn";
        Account account = new Account();
        account.setEmail(email);
        Mockito.when(accountRepository.findByEmailAndDeleteFlag(email,0)).thenReturn(Optional.of(account));
        Account account1 = accountService.getAccountByEmail(email);
        assertEquals(account,account1);
    }
    @Test
    void testcheckEmailExist() {
    }

    @Test
    void testAccountByCampus() {
        Integer campusId=1;
        String email="ngoc";
        List<DropdownListResponse> lstAccountByCampusId1 = new ArrayList<>();
        Mockito.when(accountRepository.findAllByCampusAndDelete(1,email,0)).thenReturn(lstAccountByCampusId1);
        List<DropdownListResponse> lstAccountByCampusId = accountService.listAccountByCampus(1, email);
        assertEquals(0,lstAccountByCampusId.size());
    }
}