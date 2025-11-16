package com.example.taxcalculator.service;
import com.example.taxcalculator.model.User;
import com.example.taxcalculator.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class AuthService {
    private final UserRepository userRepo;
    public AuthService(UserRepository userRepo){ this.userRepo = userRepo; }
    public User register(String name, String email, String password) {
        Optional<User> exists = userRepo.findByEmail(email);
        if (exists.isPresent()) { throw new RuntimeException("Email already registered"); }
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        User u = new User(name, email, hash);
        return userRepo.save(u);
    }
    public User authenticate(String email, String password) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!BCrypt.checkpw(password, user.getPasswordHash())) { throw new RuntimeException("Invalid credentials"); }
        return user;
    }
    public User findById(String id){ return userRepo.findById(id).orElse(null); }
}
