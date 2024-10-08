package com.example.backendkino.service;

import com.example.backendkino.model.Admin;
import com.example.backendkino.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApiServiceGetAdminsImpl implements ApiServiceGetAdmins {
    @Autowired
    AdminRepository adminRepository;

@Override
    public Optional<Admin> authenticate(String username, String password){
        Optional<Admin> optionalAdmin = adminRepository.findByUsername(username);
        if (optionalAdmin.isPresent()){
            Admin admin = optionalAdmin.get();
            if (admin.getPassword().equals(password)){
                return Optional.of(admin);
            }
        }
        return Optional.empty();
    }


}
