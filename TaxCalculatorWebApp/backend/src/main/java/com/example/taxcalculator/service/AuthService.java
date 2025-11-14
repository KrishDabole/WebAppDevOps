package com.example.taxcalculator.service;

import com.example.taxcalculator.model.User;
import com.example.taxcalculator.model.SessionToken;
import com.example.taxcalculator.repo.UserRepository;
import com.example.taxcalculator.repo.SessionTokenRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final SessionTokenRepository tokenRepo;

    public AuthService(UserRepository userRepo, SessionTokenRepository tokenRepo){
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
    }

    public User register(String name, String email, String password) {
        Optional<User> exists = userRepo.findByEmail(email);
        if (exists.isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        User u = new User(name, email, hash);
        return userRepo.save(u);
    }

    public String login(String email, String password) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!BCrypt.checkpw(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        SessionToken t = new SessionToken(user.getId());
        tokenRepo.save(t);
        return t.getToken();
    }

    public User validateToken(String token) {
        return tokenRepo.findByToken(token)
                .flatMap(st -> userRepo.findById(st.getUserId()))
                .orElseThrow(() -> new RuntimeException("Invalid token"));
    }

    public void logout(String token) {
        tokenRepo.deleteByToken(token);
    }
}
