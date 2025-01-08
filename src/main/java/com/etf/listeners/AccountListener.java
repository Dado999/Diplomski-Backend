package com.etf.listeners;

import com.etf.entities.DTO.AccountDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class AccountListener {

    @KafkaListener(topics = "Account", groupId = "groupId")
    void listener(String data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AccountDTO accountDTO = objectMapper.readValue(data, AccountDTO.class);
        System.out.println(accountDTO.toString());
    }
}
