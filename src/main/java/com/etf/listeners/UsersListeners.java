package com.etf.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UsersListeners {
    @KafkaListener(topics = "Users",groupId = "groupId")
    void listener(String data) {
        System.out.println("User topic: " + data);
    }
}
