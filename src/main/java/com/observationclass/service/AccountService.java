package com.observationclass.service;

import com.observationclass.entity.Account;
import com.observationclass.repository.AccountReqository;
import com.observationclass.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Component
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountReqository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email).get();
        UserPrincipal userPrincipal = new UserPrincipal(account);
        return userPrincipal;
    }
    public Account getAccountByEmail(String email){
      return accountRepository.findByEmail(email).get();
    }
    public boolean checkEmailExist(String email) {
        return accountRepository.existsByEmail(email);
    }
}
