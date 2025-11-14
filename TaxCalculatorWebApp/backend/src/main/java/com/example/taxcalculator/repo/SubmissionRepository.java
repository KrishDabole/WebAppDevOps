package com.example.taxcalculator.repo;

import com.example.taxcalculator.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, String> {
    List<Submission> findByUserIdOrderByCreatedAtDesc(String userId);
}
