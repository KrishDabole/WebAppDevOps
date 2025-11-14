package com.example.taxcalculator.repo;

import com.example.taxcalculator.model.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SessionTokenRepository extends JpaRepository<SessionToken, String> {
    Optional<SessionToken> findByToken(String token);
    void deleteByToken(String token);
}
