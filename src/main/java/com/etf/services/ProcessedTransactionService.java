package com.etf.services;

import com.etf.entities.DTO.ProcessesTransactionDTO;
import com.etf.entities.DTO.TransactionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ProcessedTransactionService {

    private final List<SseEmitter> transactionEmitters = new CopyOnWriteArrayList<>();

    public SseEmitter addTransactionEmitter() {
        return addEmitter(transactionEmitters);
    }

    public SseEmitter addEmitter(List<SseEmitter> emitters){
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitters.add(emitter);
        return emitter;
    }
    public void sendMessage(String message) {
        List<SseEmitter> deadEmitters = new CopyOnWriteArrayList<>();
        for (SseEmitter emitter : transactionEmitters) {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (IOException e) {
                deadEmitters.add(emitter);
            }
        }
        transactionEmitters.removeAll(deadEmitters);
    }
    @KafkaListener(topics = "ProcessedTransaction", groupId = "groupId")
    public void transactionListener(String data) {
        System.out.println(data);
        sendMessage(data);
    }
}
