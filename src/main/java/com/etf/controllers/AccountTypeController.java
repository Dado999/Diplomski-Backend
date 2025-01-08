package com.etf.controllers;

import com.etf.base.BaseController;
import com.etf.entities.DTO.AccountDTO;
import com.etf.entities.DTO.AccountTypeDTO;
import com.etf.services.KafkaProducerService;
import com.etf.services.account_type.AccountTypeService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account-types")
public class AccountTypeController extends BaseController<Integer, AccountTypeDTO> {

    private final KafkaProducerService kafkaProducerService;

    public AccountTypeController(AccountTypeService accountTypeService, KafkaProducerService kafkaProducerService) {
        super(accountTypeService, AccountTypeDTO.class);
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public AccountTypeDTO insert(@RequestBody AccountTypeDTO accountTypeDTO) throws InterruptedException {
        accountTypeDTO = super.insert(accountTypeDTO);
        kafkaProducerService.sendMessage("AccountType",accountTypeDTO);
        return accountTypeDTO;
    }
}
