package com.example.backendkino.service;

import com.example.backendkino.model.Admin;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ApiServiceGetAdmins {
    Optional<Admin> authenticate(String username, String password);
}
