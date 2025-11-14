package com.example.taxcalculator.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "session_tokens")
public class SessionToken {
    @Id
    @Column(length = 36)
    private String token = UUID.randomUUID().toString();

    @Column(nullable = false, length = 36)
    private String userId;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public SessionToken() {}
    public SessionToken(String userId){
        this.userId = userId;
    }

    public String getToken(){ return token; }
    public String getUserId(){ return userId; }
    public Instant getCreatedAt(){ return createdAt; }
}
