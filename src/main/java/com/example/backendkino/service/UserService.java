package com.example.backendkino.service;

import com.example.backendkino.config.SecurityConfiguration;
import com.example.backendkino.model.Admin;
import com.example.backendkino.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService implements IAdminService {

    private AdminRepository adminRepository;
    @Override
    public Set<Admin> findAll() {
        Set<Admin> set = new HashSet<>();
        adminRepository.findAll().forEach(set::add);
        return set;
    }

    @Override
    public Admin save(Admin user) {
//        if(user.getPassword() == null) {
            PasswordEncoder pw = SecurityConfiguration.passwordEncoder();
            user.setPassword(pw.encode(user.getPassword()));
//        }
        return adminRepository.save(user);
    }

    @Override
    public void delete(Admin object) {
            adminRepository.delete(object);
    }

    @Override
    public void deleteById(Integer aInteger) {

    }

    @Override
    public Optional<Admin> findById(Integer aInteger) {
        return adminRepository.findById(aInteger);
    }

    @Override
    public List<Admin> findByName(String name) {
        System.out.println("Userservice called findByName with argument: " + name);
        return adminRepository.findAdminsByUsername(name);
    }
}
