package com.example.backendkino.controller;

import com.example.backendkino.JwtTokenManager;
import com.example.backendkino.model.Admin;
import com.example.backendkino.model.JwtRequestModel;
import com.example.backendkino.model.JwtResponseModel;
import com.example.backendkino.repository.AdminRepository;
import com.example.backendkino.service.ApiServiceGetAdmins;
import com.example.backendkino.service.IAdminService;
import com.example.backendkino.service.JwtUserDetailsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    private ApiServiceGetAdmins apiServiceGetAdmins;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private IAdminService  iAdminService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenManager jwtTokenManager;



    @PostMapping("/login")
    public ResponseEntity<JwtResponseModel> createToken(@RequestBody JwtRequestModel request) throws Exception {
        // HttpServletRequest servletRequest is available from Spring, if needed.
        System.out.println(" JwtController createToken Call: 4" + request.getUsername());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword())
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(new JwtResponseModel("bad credentials"));
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwtToken = jwtTokenManager.generateJwtToken(userDetails);
        return ResponseEntity.ok(new JwtResponseModel(jwtToken));
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody Admin loginRequest, HttpSession httpSession) {
//        Optional<Admin> adminOptional = apiServiceGetAdmins.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
//        if (adminOptional.isPresent()) {
//            httpSession.setAttribute("admin", adminOptional.get());
//            return ResponseEntity.ok("Login succesfull");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login request");
//        }
//    }

/*    @GetMapping("/check-admin-presence")
    public ResponseEntity<String> checkForAdmin(HttpSession httpSession) {
        if (httpSession.getAttribute("admin") != null) {
            return ResponseEntity.ok("Admin session active");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No active admin session");
        }
    }
*/


    @PostMapping("/register")
    public ResponseEntity<JwtResponseModel> signup(@RequestBody JwtRequestModel request){
        System.out.println("signup: username:" + request.getUsername() + " password: " + request.getPassword() );
        Admin admin = new Admin(request.getUsername(),request.getPassword(), request.getFullName());
        if(iAdminService.findByName(admin.getUsername()).size()==0) {
            if (iAdminService.save(admin) != null) {
                return ResponseEntity.ok(new JwtResponseModel("created user: " + admin.getUsername() + " pw: " + admin.getPassword()));
            } else {
                return ResponseEntity.ok(new JwtResponseModel("error creating user: " + admin.getUsername()));
            }
        }else {
            return ResponseEntity.ok(new JwtResponseModel("error: user exists: " + admin.getUsername()));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<?> updateAdmin(@PathVariable int adminId, @RequestBody Admin adminDetails, HttpSession session) {

        Admin loggedInAdmin = (Admin) session.getAttribute("admin");
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to update an admin.");
        }

        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        if (!optionalAdmin.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found.");
        }

        Admin adminToUpdate = optionalAdmin.get();
        adminToUpdate.setUsername(adminDetails.getUsername());
        adminToUpdate.setFullName(adminDetails.getFullName());
        if (adminDetails.getPassword() != null && !adminDetails.getPassword().isEmpty()) {
            adminToUpdate.setPassword(adminDetails.getPassword());
        }

        adminRepository.save(adminToUpdate);
        return ResponseEntity.ok(adminToUpdate);
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<String> deleteAdmin(@PathVariable int adminId, HttpSession session) {

        Admin loggedInAdmin = (Admin) session.getAttribute("admin");
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to delete an admin.");
        }

        if (loggedInAdmin.getAdminId() == adminId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot delete your own account.");
        }

        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        if (!optionalAdmin.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found.");
        }

        adminRepository.deleteById(adminId);
        return ResponseEntity.ok("Admin deleted successfully.");
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<?> getAdminById(@PathVariable int adminId, HttpSession session) {

        Admin loggedInAdmin = (Admin) session.getAttribute("admin");
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to view admin details.");
        }

        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        if (!optionalAdmin.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found.");
        }

        return ResponseEntity.ok(optionalAdmin.get());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAdmins(HttpSession session) {

        Admin loggedInAdmin = (Admin) session.getAttribute("admin");
        if (loggedInAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to view admins.");
        }

        List<Admin> admins = adminRepository.findAll();
        return ResponseEntity.ok(admins);
    }
}


