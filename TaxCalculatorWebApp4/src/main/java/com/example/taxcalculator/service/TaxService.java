package com.example.taxcalculator.service;

import org.springframework.stereotype.Service;
import com.example.taxcalculator.dto.TaxRequest;
import com.example.taxcalculator.dto.TaxResponse;

@Service
public class TaxService {

    public TaxResponse calculateTax(TaxRequest request) {
        double totalPackage = request.getTotalPackage();
        double variablePay = request.getVariablePay();
        double nps = request.getNpsContribution();
        String regime = request.getRegime() == null ? "NEW" : request.getRegime().toUpperCase();

        double taxableIncome = totalPackage - variablePay - nps;
        double standardDeduction = 50000; // as per current rules
        taxableIncome -= standardDeduction;

        double tax = 0;
        if (regime.equals("NEW")) {
            tax = calculateNewRegimeTax(taxableIncome);
        } else {
            tax = calculateOldRegimeTax(taxableIncome);
        }

        double rebate = taxableIncome <= 700000 ? tax : 0; // Section 87A for new regime
        tax -= rebate;
        double cess = tax * 0.04;
        double totalTax = tax + cess;

        TaxResponse response = new TaxResponse();
        response.setTaxableIncome(taxableIncome);
        response.setTaxBeforeCess(tax);
        response.setCess(cess);
        response.setTotalTax(totalTax);
        response.setMonthlyTax(totalTax / 12);
        return response;
    }

    private double calculateNewRegimeTax(double income) {
        double tax = 0;
        double[] slabs = {300000, 600000, 900000, 1200000, 1500000};
        double[] rates = {0.05, 0.10, 0.15, 0.20, 0.30};
        double prev = 0;
        for (int i = 0; i < slabs.length; i++) {
            if (income > slabs[i]) {
                tax += (slabs[i] - prev) * rates[i];
                prev = slabs[i];
            } else {
                tax += (income - prev) * rates[i];
                return tax;
            }
        }
        if (income > slabs[slabs.length - 1]) {
            tax += (income - slabs[slabs.length - 1]) * rates[rates.length - 1];
        }
        return tax;
    }

    private double calculateOldRegimeTax(double income) {
        double tax = 0;
        double[] slabs = {250000, 500000, 1000000};
        double[] rates = {0.05, 0.20, 0.30};
        double prev = 0;
        for (int i = 0; i < slabs.length; i++) {
            if (income > slabs[i]) {
                tax += (slabs[i] - prev) * rates[i];
                prev = slabs[i];
            } else {
                tax += (income - prev) * rates[i];
                return tax;
            }
        }
        if (income > slabs[slabs.length - 1]) {
            tax += (income - slabs[slabs.length - 1]) * rates[rates.length - 1];
        }
        return tax;
    }
}
