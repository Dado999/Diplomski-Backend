package com.etf.listeners;

import com.etf.entities.DTO.ProcessesTransactionDTO;
import com.etf.entities.DTO.TransactionDTO;
import com.etf.entities.DTO.TransactionLogDTO;
import com.etf.entities.DTO.TransactionStatusDTO;
import com.etf.services.transaction_log.TransactionLogService;
import com.etf.services.transaction_status.TransactionStatusService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class AnalyticsService {

    private List<TransactionDTO> transactionDTOList = new ArrayList<>();
    private final List<SseEmitter> transactionEmitters = new CopyOnWriteArrayList<>();

    @Autowired
    private final TransactionStatusService transactionStatusService;

    @Autowired
    private final TransactionLogService transactionLogService;

    ObjectMapper objectMapper = new ObjectMapper();

    public AnalyticsService(TransactionStatusService transactionStatusService,
                            TransactionLogService transactionLogService
    ) {
        this.transactionStatusService = transactionStatusService;
        this.transactionLogService = transactionLogService;
    }

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
    public void sendMessage(String message){
        sendMessage(message, transactionEmitters);
    }
    public static void sendMessage(String message, List<SseEmitter> transactionEmitters) {
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

    @KafkaListener(topics = "Transaction", groupId = "group1")
    public void transactionListener(String data) throws JsonProcessingException {
        TransactionDTO transactionDTO = objectMapper.readValue(data, TransactionDTO.class);
        transactionDTOList.add(transactionDTO);
        ProcessesTransactionDTO processesTransactionDTO = new ProcessesTransactionDTO();
        processesTransactionDTO.setTransactionDTO(transactionDTO);
        processesTransactionDTO.setTotalAmount(getTotalAmount());
        processesTransactionDTO.setAverageAmount(getAverageAmount());
        processesTransactionDTO.setOrigin(getStatusOrigin(transactionLogService.findByTransactionId(transactionDTO.getId())));
        System.out.println("Received message: " + processesTransactionDTO);
        sendMessage(objectMapper.writeValueAsString(processesTransactionDTO));
    }
    //TODO-DF Use kafka streams for this
    public String getTotalAmount(){
        return String.valueOf(transactionDTOList
                .stream()
                .mapToDouble(TransactionDTO::amountToDouble)
                .sum());
    }
    //TODO-DF Use kafka streams for this
    public String getAverageAmount(){
        return String.valueOf(transactionDTOList
                .stream()
                .mapToDouble(TransactionDTO::amountToDouble)
                .average()
                .orElse(0.0));
    }
    private String getStatusOrigin(TransactionLogDTO transactionStatusDTO) {
        return transactionStatusService.findByID(transactionStatusDTO.getStatusId(), TransactionStatusDTO.class).getCode();
    }
}
