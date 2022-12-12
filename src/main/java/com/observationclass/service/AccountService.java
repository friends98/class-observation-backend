package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.Account;
import com.observationclass.model.ERole;
import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.AccountRepository;
import com.observationclass.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@Component
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmailAndDeleteFlag(email, Constants.NONE_COMPLETE).get();
        UserPrincipal userPrincipal = new UserPrincipal(account);
        return userPrincipal;
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmailAndDeleteFlag(email, Constants.DELETE_NONE).get();
    }

    public boolean checkEmailExist(String email) {
        return accountRepository.existsByEmail(email);
    }

    public List<DropdownListResponse> listAccountByCampus(Integer campusId, String email) {
        List<DropdownListResponse> listAccountByCampus = accountRepository.findAllByCampusAndDelete(campusId, email, Constants.DELETE_NONE);
        return listAccountByCampus;
    }
    public List<DropdownListResponse> listAccountByCampusAndRole(Integer campusId,String email) {
        List<DropdownListResponse> listAccountByCampusAndRole = accountRepository.findAllByCampusIdAndRole(campusId,
                email, Constants.DELETE_NONE, Constants.ROLE_TEACHER);
        return listAccountByCampusAndRole;
    }

}
