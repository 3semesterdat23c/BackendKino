package com.example.backendkino.service;

import com.example.backendkino.model.Admin;

import java.util.List;

public interface IAdminService extends ICrudService<Admin,Integer> {
    List<Admin> findByName(String name);
}
