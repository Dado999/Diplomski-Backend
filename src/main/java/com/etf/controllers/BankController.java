package com.etf.controllers;

import com.etf.base.BaseController;
import com.etf.entities.DTO.AccountTypeDTO;
import com.etf.entities.DTO.BankDTO;
import com.etf.services.KafkaProducerService;
import com.etf.services.bank.BankService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banks")
public class BankController extends BaseController<Integer, BankDTO> {

    private final KafkaProducerService kafkaProducerService;

    public BankController(BankService bankService, KafkaProducerService kafkaProducerService) {
        super(bankService, BankDTO.class);
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public BankDTO insert(@RequestBody BankDTO bankDTO) throws InterruptedException {
        bankDTO = super.insert(bankDTO);
        kafkaProducerService.sendMessage("Bank",bankDTO);
        return bankDTO;
    }
}
