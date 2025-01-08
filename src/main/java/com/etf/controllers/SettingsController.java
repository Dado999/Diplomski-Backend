package com.etf.controllers;

import com.etf.entities.DTO.TransactionAmountLimitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
@CrossOrigin(origins = "http://localhost:4200")
public class SettingsController {

    @PostMapping("/set-limit")
    public void setTransactionAmountLimit(@RequestBody TransactionAmountLimitDTO limitDTO) {
        if (limitDTO.getMinValue() != null && limitDTO.getMaxValue() != null && limitDTO.getMaxValue() >= limitDTO.getMinValue()) {
            System.setProperty("application.lower-limit", limitDTO.getMinValue().toString());
            System.setProperty("application.upper-limit", limitDTO.getMaxValue().toString());
        }
    }
}
