package com.example.taxcalculator.controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "${frontend.origin:http://localhost:3000}")
public class AuthController {
    @PostMapping("/register")
    public Map<String,String> register(@RequestBody Map<String,String> body){
        // dummy response (for packaging). Real project includes DB-backed auth.
        return Map.of("id","local","email", body.get("email"));
    }
    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Map<String,String> body){
        return Map.of("token","dummy-token");
    }
}
