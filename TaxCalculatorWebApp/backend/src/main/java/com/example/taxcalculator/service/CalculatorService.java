package com.example.taxcalculator.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CalculatorService {

    private static long excelRound(double v) {
        return Math.round(v);
    }

    private static double ceilingToNearest(double value, double significance) {
        if (significance == 0) return value;
        double div = value / significance;
        double ceil = Math.ceil(div) * significance;
        return ceil;
    }

    public Map<String, Object> calculateTax(double grossTotalPackage, double variablePay, double npsContribution, String financialYearStr) {
        Map<String,Object> res = new HashMap<>();

        double C12 = grossTotalPackage - variablePay;
        double C14 = C12 * 0.40;
        double C15 = C12 * 0.20;
        double C17 = npsContribution;
        double C21 = C14 * 0.12;
        double C19 = 0.0;
        double C16 = C12 - C14 - C15 - C17 - C19 - C21;
        double C20 = C14 + C15 + C16 + C17;
        double D20 = C20 / 12.0;
        double C23 = ceilingToNearest(C20 + 1.0, 10.0);
        double C24 = 75000.0;
        double C25 = C17;
        double C28 = 0.0;
        double C29 = 0.0;
        double C30 = C23 - C24 - C25 - C28 - C29;

        res.put("C12", C12);
        res.put("C14", C14);
        res.put("C15", C15);
        res.put("C16", C16);
        res.put("C17", C17);
        res.put("C20", C20);
        res.put("C21", C21);
        res.put("C23", C23);
        res.put("C24", C24);
        res.put("C25", C25);
        res.put("C30", C30);

        boolean fyHas2026 = financialYearStr != null && financialYearStr.contains("2026");

        // New regime calculation
        double newC32;
        if (C30 <= 400000) newC32 = 0.0;
        else if (C30 <= 800000) newC32 = (C30 - 400000.0) * 0.05;
        else if (C30 <= 1200000) newC32 = 20000.0 + (C30 - 800000.0) * 0.10;
        else if (C30 <= 1600000) newC32 = 60000.0 + (C30 - 1200000.0) * 0.15;
        else if (C30 <= 2000000) newC32 = 120000.0 + (C30 - 1600000.0) * 0.20;
        else if (C30 <= 2400000) newC32 = 200000.0 + (C30 - 2000000.0) * 0.25;
        else newC32 = 300000.0 + (C30 - 2400000.0) * 0.30;
        newC32 = excelRound(newC32);
        double newC33 = 0.0;
        if (fyHas2026) {
            if (C30 <= 1200000.0) newC33 = Math.min(60000.0, newC32);
            else newC33 = 0.0;
        } else {
            if (C30 <= 700000.0) newC33 = Math.min(25000.0, newC32);
            else newC33 = 0.0;
        }
        double newC35 = excelRound((newC32 - newC33) * 0.04);
        double newC37 = excelRound((newC32 - newC33) + (newC32 - newC33) * 0.04);

        res.put("new_C32", newC32);
        res.put("new_C33", newC33);
        res.put("new_C35", newC35);
        res.put("new_C37", newC37);

        // Old regime calculation
        double oldC32;
        if (C30 <= 300000) oldC32 = 0.0;
        else if (C30 <= 700000) oldC32 = (C30 - 300000.0) * 0.05;
        else if (C30 <= 1000000) oldC32 = 20000.0 + (C30 - 700000.0) * 0.10;
        else if (C30 <= 1200000) oldC32 = 50000.0 + (C30 - 1000000.0) * 0.15;
        else if (C30 <= 1500000) oldC32 = 80000.0 + (C30 - 1200000.0) * 0.20;
        else oldC32 = 140000.0 + (C30 - 1500000.0) * 0.30;
        oldC32 = excelRound(oldC32);
        double oldC33 = 0.0;
        if (fyHas2026) {
            if (C30 <= 1200000.0) oldC33 = Math.min(60000.0, oldC32);
            else oldC33 = 0.0;
        } else {
            if (C30 <= 700000.0) oldC33 = Math.min(25000.0, oldC32);
            else oldC33 = 0.0;
        }
        double oldC35 = excelRound((oldC32 - oldC33) * 0.04);
        double oldC37 = excelRound((oldC32 - oldC33) + (oldC32 - oldC33) * 0.04);

        res.put("old_C32", oldC32);
        res.put("old_C33", oldC33);
        res.put("old_C35", oldC35);
        res.put("old_C37", oldC37);

        res.put("totalTax_new", newC37);
        res.put("totalTax_old", oldC37);

        return res;
    }
}
