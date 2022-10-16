package com.observationclass.service;

import com.observationclass.entity.Role;
import com.observationclass.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getListRole(){
        List<Role> lstRole =roleRepository.findAll();
        return lstRole;
    }
}
