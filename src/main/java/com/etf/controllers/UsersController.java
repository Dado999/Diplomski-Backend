package com.etf.controllers;

import com.etf.base.BaseController;
import com.etf.entities.DTO.TransactionStatusDTO;
import com.etf.entities.DTO.UsersDTO;
import com.etf.services.KafkaProducerService;
import com.etf.services.users.UsersService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController extends BaseController<Integer, UsersDTO> {

    private final KafkaProducerService kafkaProducerService;

    public UsersController(UsersService usersService, KafkaProducerService kafkaProducerService) {
        super(usersService,UsersDTO.class);
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public UsersDTO insert(@RequestBody UsersDTO usersDTO) throws InterruptedException {
        usersDTO = super.insert(usersDTO);
        kafkaProducerService.sendMessage("Users",usersDTO);
        return usersDTO;
    }
}
