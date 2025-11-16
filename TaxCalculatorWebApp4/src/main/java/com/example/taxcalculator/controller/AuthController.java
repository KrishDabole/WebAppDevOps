package com.example.taxcalculator.controller;

import com.example.taxcalculator.model.User;
import com.example.taxcalculator.repository.UserRepository;
import com.example.taxcalculator.security.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> creds) {
        User user = userRepository.findByUsername(creds.get("username")).orElse(null);
        if (user != null && encoder.matches(creds.get("password"), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            return Map.of("token", token);
        }
        throw new RuntimeException("Invalid credentials");
    }
}
