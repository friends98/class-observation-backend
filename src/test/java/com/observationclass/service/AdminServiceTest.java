package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.Account;
import com.observationclass.entity.Role;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.AccountRequest;
import com.observationclass.repository.AccountRepository;
import com.observationclass.repository.dao.AccountDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)


class AdminServiceTest {
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private AccountDao accountDao;
    @InjectMocks
    private AdminService adminService;
    @Mock
    private AccountService accountService;





    private Account account;
    private Role role;
    @BeforeEach

    void setUp(){
        accountService=new AccountService();
        Mockito.mock(AccountRepository.class);
        Mockito.mock(AccountDao.class);
    }
    @Test
    void getListAccount() {
        List<Account> listOfAccount = new ArrayList<>();
        listOfAccount.add(new Account());
        listOfAccount.add(new Account());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(Constants.HTTP_CODE_200);
        apiResponse.setMessage(Constants.SUCCESS);
        apiResponse.setItems(listOfAccount);
        Mockito.when(accountRepository.findAllByDeleteFlag(Constants.DELETE_NONE)).thenReturn(listOfAccount);
        List<Account> listOfAccountService =(ArrayList)adminService.getListAccount().getItems();
        assertEquals(listOfAccount.size(),listOfAccountService.size());
    }

    @Test
    void testGetAccountByRole_WhenRoleValid() {
        List<Object> listOfAccountByRoleMock = new ArrayList<>();
        listOfAccountByRoleMock.add(new Account());
        listOfAccountByRoleMock.add(new Account());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(Constants.HTTP_CODE_200);
        apiResponse.setMessage(Constants.SUCCESS);
        apiResponse.setItems(listOfAccountByRoleMock);
        Mockito.when(accountDao.listAccountByRole(1,"ngoc")).thenReturn(listOfAccountByRoleMock);
        List<Object> listOfAccountByRoleActual = (ArrayList)adminService.getAccountByRole(1,"ngoc").getItems();
        assertEquals(listOfAccountByRoleMock.size(),listOfAccountByRoleActual.size());

    }
    @Test
    void testGetAccountByRole_WhenRoleInvalid() {
        List<Object> listOfAccountByRoleMock = new ArrayList<>();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(Constants.HTTP_CODE_200);
        apiResponse.setMessage(Constants.SUCCESS);
        apiResponse.setItems(listOfAccountByRoleMock);
        Mockito.when(accountDao.listAccountByRole(anyInt(),anyString())).thenReturn(listOfAccountByRoleMock);
        List<Object> listOfAccountByRoleActual =
                (ArrayList)adminService.getAccountByRole(anyInt(),anyString()).getItems();
        assertTrue(listOfAccountByRoleActual.isEmpty());
    }



    @Test
    void testDeleteAccountById() throws Exception {
        Account account = new Account();
        account.setId(1);
        account.setDeleteFlag(0);

        Mockito.when(accountRepository.findByIdAndDeleteFlag(anyInt(),anyInt())).thenReturn(Optional.ofNullable(account));
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        ApiResponse apiResponseActual = adminService.deleteAccountById(account.getId());
        assertThat(apiResponseActual).isNotNull();
    }

    @Test
    void testAddNewAccount_WhenEmail_NotExits() throws Exception {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setEmail("ngocquang@gmail.com");
        accountRequest.setCampusId(1);
        accountRequest.setUserName("dao ngoc quang");
        Account account = new Account();
        account.setUserName(accountRequest.getUserName());
        account.setEmail(accountRequest.getEmail());
        account.setCampusId(accountRequest.getCampusId());
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        ApiResponse apiResponseActual = adminService.addNewAccount(accountRequest);
        assertThat(apiResponseActual).isNotNull();
    }
    @Test
    void testAddNewAccount_WhenEmail_Exits() throws Exception {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setEmail("ngocquang@gmail.com");
        accountRequest.setCampusId(1);
        accountRequest.setUserName("dao ngoc quang");
        Account account = new Account();
        account.setUserName(accountRequest.getUserName());
        account.setEmail(accountRequest.getEmail());
        account.setCampusId(accountRequest.getCampusId());
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        ApiResponse apiResponseActual = adminService.addNewAccount(accountRequest);
        assertThat(apiResponseActual).isNotNull();
    }


    @Test
    void testUpdateAccount_WhenValid() throws Exception {


        Account account = new Account();
        account.setId(1);
        account.setCampusId(1);
        account.setEmail("quangdnhe141096@fpt.edu.vn");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role());
        account.setRoles(roles);
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setId(1);

        Mockito.when(accountRepository.findByIdAndDeleteFlag(anyInt(),anyInt())).thenReturn(Optional.ofNullable(account));
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        ApiResponse apiResponseActual = adminService.updateAccount(accountRequest);
        assertThat(apiResponseActual).isNotNull();

    }

    @Test
    void setAccount() {
        Account account = new Account();
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setEmail("ngocquang@gmail.com");
        accountRequest.setCampusId(1);
        accountRequest.setUserName("dao ngoc quang");
        adminService.setAccount(account,accountRequest);
        assertThat(account);
    }


}