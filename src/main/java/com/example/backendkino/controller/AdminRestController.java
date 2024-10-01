package com.example.backendkino.controller;

import com.example.backendkino.model.Admin;
import com.example.backendkino.repository.AdminRepository;
import com.example.backendkino.service.ApiServiceGetAdmins;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    ApiServiceGetAdmins apiServiceGetAdmins;
    @Autowired
    AdminRepository adminRepository;

@PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody Admin loginRequest, HttpSession httpSession){
    Optional<Admin> adminOptional = apiServiceGetAdmins.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    if (adminOptional.isPresent()){
        httpSession.setAttribute("admin", adminOptional.get());
        return ResponseEntity.ok("Login succesfull");
    }else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login request");
    }
}
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Admin admin) {
        if (adminRepository.findByUsername(admin.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        adminRepository.save(admin);
        return ResponseEntity.ok("Admin registered successfully");
    }

}
