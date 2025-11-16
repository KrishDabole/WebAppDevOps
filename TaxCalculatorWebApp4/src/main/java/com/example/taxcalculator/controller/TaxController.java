package com.example.taxcalculator.controller;

import org.springframework.web.bind.annotation.*;
import com.example.taxcalculator.dto.TaxRequest;
import com.example.taxcalculator.dto.TaxResponse;
import com.example.taxcalculator.service.TaxService;

@RestController
@RequestMapping("/api/tax")
public class TaxController {

    private final TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @PostMapping("/calculate")
    public TaxResponse calculate(@RequestBody TaxRequest request) {
        return taxService.calculateTax(request);
    }
}
