package com.example.taxcalculator.controller;

import com.example.taxcalculator.model.Submission;
import com.example.taxcalculator.model.User;
import com.example.taxcalculator.repo.SubmissionRepository;
import com.example.taxcalculator.service.AuthService;
import com.example.taxcalculator.service.CalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/calculator")
@CrossOrigin(origins = "${frontend.origin:http://localhost:3000}")
public class CalculatorController {
    private final AuthService auth;
    private final CalculatorService calc;
    private final SubmissionRepository submissionRepo;
    private final ObjectMapper mapper = new ObjectMapper();

    public CalculatorController(AuthService auth, CalculatorService calc, SubmissionRepository submissionRepo){
        this.auth = auth;
        this.calc = calc;
        this.submissionRepo = submissionRepo;
    }

    private String extractToken(String header){
        if (header != null && header.startsWith("Bearer ")) return header.substring(7);
        throw new RuntimeException("Missing token");
    }

    @PostMapping("/submit")
    public Map<String,Object> submit(@RequestHeader(name="Authorization", required=false) String authHeader,
                                     @RequestBody Map<String,Object> body){
        String token = extractToken(authHeader);
        User user = auth.validateToken(token);

        double gross = ((Number)body.getOrDefault("grossTotalPackage", 0)).doubleValue();
        double variable = ((Number)body.getOrDefault("variablePay", 0)).doubleValue();
        double nps = ((Number)body.getOrDefault("npsContribution", 0)).doubleValue();
        String fy = (String) body.getOrDefault("financialYearStr", "");

        Map<String,Object> result = calc.calculateTax(gross, variable, nps, fy);

        try {
            Submission s = new Submission(user.getId(), mapper.writeValueAsString(body), mapper.writeValueAsString(result));
            submissionRepo.save(s);
        } catch (Exception e) {
            // ignore save errors
        }
        return result;
    }

    @GetMapping("/history")
    public java.util.List<Submission> history(@RequestHeader(name="Authorization", required=false) String authHeader){
        String token = extractToken(authHeader);
        User user = auth.validateToken(token);
        return submissionRepo.findByUserIdOrderByCreatedAtDesc(user.getId());
    }
}
