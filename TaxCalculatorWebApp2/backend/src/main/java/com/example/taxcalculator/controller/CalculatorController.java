package com.example.taxcalculator.controller;
import com.example.taxcalculator.service.CalculatorService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/calculator")
@CrossOrigin(origins = "${frontend.origin:http://localhost:3000}")
public class CalculatorController {
    private final CalculatorService calc = new CalculatorService();
    @PostMapping("/submit")
    public Map<String,Object> submit(@RequestBody Map<String,Object> body){
        double gross = ((Number)body.getOrDefault("grossTotalPackage", 0)).doubleValue();
        double variable = ((Number)body.getOrDefault("variablePay", 0)).doubleValue();
        double nps = ((Number)body.getOrDefault("npsContribution", 0)).doubleValue();
        String fy = (String) body.getOrDefault("financialYearStr", "");
        return calc.calculateTax(gross, variable, nps, fy);
    }
}
