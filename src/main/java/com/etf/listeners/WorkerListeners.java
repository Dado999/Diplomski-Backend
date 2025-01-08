package com.etf.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class WorkerListeners {

    @KafkaListener(topics = "Worker",groupId = "groupId")
    void listener(String data) {
        System.out.println("Worker topic: " + data);
    }
}
