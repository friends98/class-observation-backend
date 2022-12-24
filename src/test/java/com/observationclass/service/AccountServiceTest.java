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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)

class AccountServiceTest {

    @MockBean
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void testGetAccountByEmail_WhenExist() {
        String email="quang41096@fpt.edu.vn";
        Account account = new Account();
        account.setEmail(email);
        Mockito.when(accountRepository.findByEmailAndDeleteFlag(email,0)).thenReturn(Optional.of(account));
        Account account1 = accountService.getAccountByEmail(email);
        assertEquals(account,account1);
    }
    @Test
    void testGetAccountByEmail_NotExist() {
        String email="quang41096@fpt.edu.vn";
        Account account = new Account();
        Account accountTest = new Account();
        Mockito.when(accountRepository.findByEmailAndDeleteFlag(email,0)).thenReturn(Optional.of(account));
        Account account1 = accountService.getAccountByEmail(email);
        assertEquals(account.getEmail(),null);
    }
    @Test
    void testcheckEmailExist() {
        String email="ngocquang2000fpt@gmail.com";
        Mockito.when(accountRepository.existsByEmail(email)).thenReturn(true);
        boolean checkEmail = accountService.checkEmailExist(email);
        assertEquals(true,checkEmail);
    }
    @Test
    void testcheckEmailNotExist() {
        String email="abc2000fpt@gmail.com";
        Mockito.when(accountRepository.existsByEmail(email)).thenReturn(false);
        boolean checkEmail = accountService.checkEmailExist(email);
        assertEquals(false,checkEmail);
    }

    @Test
    void testAccountByCampus() {
        Integer campusId=1;
        String email="ngoc";
        List<Object> lstAccountByCampusId1 = new ArrayList<>();
        List<DropdownListResponse> lstAccountByCampusId2 = new ArrayList<>();
        lstAccountByCampusId1.add(new Object());
        lstAccountByCampusId1.add(new Object());
        class obj implements DropdownListResponse{

            @Override
            public Integer getValue() {
                return null;
            }

            @Override
            public Integer setValue(Integer value) {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String setName() {
                return null;
            }
        }
        lstAccountByCampusId2.add(new obj());
        lstAccountByCampusId2.add(new obj());
        Mockito.when(accountRepository.findAllByCampusAndDelete(1,email,0)).thenReturn(lstAccountByCampusId2);
        List<DropdownListResponse> lstAccountByCampusId = accountService.listAccountByCampus(1, email);
        assertEquals(2,lstAccountByCampusId.size());
    }
    @Test
    void testAccount_NotExist_Campus() {
        Integer campusId=1;
        String email="ngoc";
        List<DropdownListResponse> lstAccountByCampusId1 = new ArrayList<>();
        Mockito.when(accountRepository.findAllByCampusAndDelete(1,email,0)).thenReturn(lstAccountByCampusId1);
        List<DropdownListResponse> lstAccountByCampusId = accountService.listAccountByCampus(1, email);
        assertEquals(0,lstAccountByCampusId.size());
    }

    @Test
    void testAccount_WithSearchEmail() {
        Integer campusId=1;
        String email="ngoc";
        List<Object> lstAccountByCampusId1 = new ArrayList<>();
        List<DropdownListResponse> lstAccountByCampusId2 = new ArrayList<>();
        lstAccountByCampusId1.add(new Object());
        lstAccountByCampusId1.add(new Object());
        class obj implements DropdownListResponse{

            @Override
            public Integer getValue() {
                return null;
            }

            @Override
            public Integer setValue(Integer value) {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String setName() {
                return null;
            }
        }
        lstAccountByCampusId2.add(new obj());
        lstAccountByCampusId2.add(new obj());
        Mockito.when(accountRepository.findAllByCampusAndDelete(1,email,0)).thenReturn(lstAccountByCampusId2);
        List<DropdownListResponse> lstAccountByCampusId = accountService.listAccountByCampus(1, email);
        assertEquals(2,lstAccountByCampusId.size());
    }
}