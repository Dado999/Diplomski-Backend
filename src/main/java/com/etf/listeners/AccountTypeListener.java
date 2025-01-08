package com.etf.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AccountTypeListener {
    @KafkaListener(topics = "AccountType", groupId = "groupId")
    void listener(String data) { System.out.println("Account Type topic: " + data); }
}
