package com.example.taxcalculator.controller;

import com.example.taxcalculator.model.User;
import com.example.taxcalculator.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "${frontend.origin:http://localhost:3000}")
public class AuthController {
    private final AuthService auth;

    public AuthController(AuthService auth){
        this.auth = auth;
    }

    @PostMapping("/register")
    public Map<String,String> register(@RequestBody Map<String,String> body){
        String name = body.get("name");
        String email = body.get("email");
        String password = body.get("password");
        User u = auth.register(name,email,password);
        return Map.of("id", u.getId(), "email", u.getEmail());
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Map<String,String> body){
        String email = body.get("email");
        String password = body.get("password");
        String token = auth.login(email,password);
        return Map.of("token", token);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader(name="Authorization", required=false) String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            auth.logout(token);
        }
    }
}
