package com.example.backendkino.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequestModel  {
    private String username;
    private String password;
    private String fullName;
}
