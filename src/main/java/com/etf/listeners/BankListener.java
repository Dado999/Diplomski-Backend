package com.etf.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BankListener {
    @KafkaListener(topics = "Bank", groupId = "groupId")
    void listener(String data) {
        System.out.println("Bank topic: " + data);
    }
}
