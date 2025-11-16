package com.example.taxcalculator.controller;
import com.example.taxcalculator.model.User;
import com.example.taxcalculator.security.JwtUtil;
import com.example.taxcalculator.service.AuthService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "${frontend.origin:http://localhost:3000}")
public class AuthController {
    private final AuthService auth;
    private final JwtUtil jwt;
    public AuthController(AuthService auth, JwtUtil jwt) { this.auth = auth; this.jwt = jwt; }
    @PostMapping("/register")
    public Map<String,String> register(@RequestBody Map<String,String> body){
        String name = body.get("name"); String email = body.get("email"); String password = body.get("password");
        User u = auth.register(name,email,password);
        String token = jwt.generateToken(u.getId());
        return Map.of("id", u.getId(), "email", u.getEmail(), "token", token);
    }
    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Map<String,String> body){
        String email = body.get("email"); String password = body.get("password");
        User u = auth.authenticate(email,password);
        String token = jwt.generateToken(u.getId());
        return Map.of("token", token);
    }
}
