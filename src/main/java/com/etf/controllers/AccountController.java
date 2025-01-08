package com.etf.controllers;

import com.etf.base.BaseController;
import com.etf.entities.DTO.AccountDTO;
import com.etf.services.KafkaProducerService;
import com.etf.services.account.AccountService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController extends BaseController<Integer, AccountDTO> {

    private final KafkaProducerService kafkaProducerService;

    public AccountController(AccountService accountService, KafkaProducerService kafkaProducerService) {
        super(accountService,AccountDTO.class);
        this.kafkaProducerService = kafkaProducerService;
    }


    @Override
    public AccountDTO insert(@RequestBody AccountDTO accountDTO) throws InterruptedException {
        accountDTO = super.insert(accountDTO);
        kafkaProducerService.sendMessage("Account",accountDTO);
        return accountDTO;
    }
}
