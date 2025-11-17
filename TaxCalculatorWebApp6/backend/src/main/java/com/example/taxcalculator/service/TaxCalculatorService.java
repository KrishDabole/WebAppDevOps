package com.example.taxcalculator.service;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class TaxCalculatorService {
    public Map<String, Object> calculateFullTax(double totalPackage, double variablePay, double npsContribution) {
        Map<String, Object> result = new HashMap<>();
        double basicSalary = totalPackage * 0.4;
        double hra = totalPackage * 0.2;
        double splAllowance = totalPackage * 0.3;
        double providentFund = basicSalary * 0.12;
        double professionalTax = 2400;
        double standardDeduction = 50000;
        double grossSalary = totalPackage;
        double deductions = providentFund + professionalTax + npsContribution + standardDeduction;
        double taxableIncome = grossSalary - deductions;

        // Old Regime Tax Calculation
        double oldTax = 0;
        if (taxableIncome <= 250000) oldTax = 0;
        else if (taxableIncome <= 500000) oldTax = (taxableIncome - 250000) * 0.05;
        else if (taxableIncome <= 1000000) oldTax = 12500 + (taxableIncome - 500000) * 0.20;
        else oldTax = 112500 + (taxableIncome - 1000000) * 0.30;
        double oldCess = oldTax * 0.04;
        double oldTotalTax = oldTax + oldCess;

        // New Regime Tax Calculation
        double newTax = 0;
        if (taxableIncome <= 300000) newTax = 0;
        else if (taxableIncome <= 600000) newTax = (taxableIncome - 300000) * 0.05;
        else if (taxableIncome <= 900000) newTax = 15000 + (taxableIncome - 600000) * 0.10;
        else if (taxableIncome <= 1200000) newTax = 45000 + (taxableIncome - 900000) * 0.15;
        else if (taxableIncome <= 1500000) newTax = 90000 + (taxableIncome - 1200000) * 0.20;
        else newTax = 150000 + (taxableIncome - 1500000) * 0.30;
        double newCess = newTax * 0.04;
        double newTotalTax = newTax + newCess;

        // Monthly breakdown
        double monthlyGross = grossSalary / 12;
        double monthlyOldTax = oldTotalTax / 12;
        double monthlyNewTax = newTotalTax / 12;

        Map<String, Double> earnings = new HashMap<>();
        earnings.put("Basic Salary", basicSalary);
        earnings.put("HRA", hra);
        earnings.put("Special Allowance", splAllowance);
        earnings.put("Variable Pay", variablePay);

        Map<String, Double> deductionsMap = new HashMap<>();
        deductionsMap.put("Provident Fund", providentFund);
        deductionsMap.put("Professional Tax", professionalTax);
        deductionsMap.put("NPS Contribution", npsContribution);
        deductionsMap.put("Standard Deduction", standardDeduction);

        result.put("earnings", earnings);
        result.put("deductions", deductionsMap);
        result.put("grossSalary", grossSalary);
        result.put("taxableIncome", taxableIncome);
        result.put("oldRegimeTax", oldTotalTax);
        result.put("newRegimeTax", newTotalTax);
        result.put("monthlyGross", monthlyGross);
        result.put("monthlyOldTax", monthlyOldTax);
        result.put("monthlyNewTax", monthlyNewTax);
        return result;
    }
}
