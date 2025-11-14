package com.example.taxcalculator.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "submissions")
public class Submission {
    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, length = 36)
    private String userId;

    @Column(columnDefinition = "text")
    private String inputJson;

    @Column(columnDefinition = "text")
    private String resultJson;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public Submission() {}
    public Submission(String userId, String inputJson, String resultJson){
        this.userId = userId;
        this.inputJson = inputJson;
        this.resultJson = resultJson;
    }

    public String getId(){ return id; }
    public String getUserId(){ return userId; }
    public String getInputJson(){ return inputJson; }
    public String getResultJson(){ return resultJson; }
    public Instant getCreatedAt(){ return createdAt; }
}
