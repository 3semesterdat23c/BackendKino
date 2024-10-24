package com.example.backendkino.controller;

import com.example.backendkino.JwtTokenManager;
import com.example.backendkino.model.Admin;
import com.example.backendkino.model.JwtRequestModel;
import com.example.backendkino.model.JwtResponseModel;
import com.example.backendkino.service.IAdminService;
import com.example.backendkino.service.JwtUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class JwtController {
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenManager jwtTokenManager;
    @Autowired
    private IAdminService iAdminService;



   


    @PostMapping("/getSecret")
    public ResponseEntity<Map> getSecret() {
        System.out.println("getSecret is called");
        Map<String,String > map = new HashMap<>();
        map.put("message","this is secret from server");
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Map> deleteUser(@RequestBody Admin user) { // hvis man kommer hertil, er token OK
        System.out.println("deleteUser is called with user: " + user.getUsername());
        // evt. findById, som finder hele objektet fra MySQL, inkl. id.
        List<Admin> users =  iAdminService.findByName(user.getUsername());
        Admin userToDelete = users.get(0);
        iAdminService.delete(userToDelete);
        Map<String,String > map = new HashMap<>();
        map.put("message","user deleted, if found " + user.getUsername());
        return ResponseEntity.ok(map);
    }

}