package com.example.taxcalculator.model;
import jakarta.persistence.*;
import java.util.UUID;
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String passwordHash;
    public User() {}
    public User(String name, String email, String passwordHash) { this.name = name; this.email = email; this.passwordHash = passwordHash; }
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}
