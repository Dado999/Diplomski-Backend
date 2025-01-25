package com.etf.controllers;

import com.etf.entities.DTO.SettingsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
@CrossOrigin(origins = "http://localhost:4200")
public class SettingsController {

    @PostMapping("/set-limit")
    public void setTransactionAmountLimit(@RequestBody SettingsDTO limitDTO) {
            System.setProperty("application.transaction-number", limitDTO.getTransactionNumber().toString());
            System.setProperty("application.duration", limitDTO.getDuration().toString());
    }
}
