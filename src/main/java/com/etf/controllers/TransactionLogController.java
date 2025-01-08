package com.etf.controllers;

import com.etf.base.BaseController;
import com.etf.entities.DTO.TransactionDTO;
import com.etf.entities.DTO.TransactionLogDTO;
import com.etf.services.KafkaProducerService;
import com.etf.services.transaction_log.TransactionLogService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction-logs")
public class TransactionLogController extends BaseController<Integer, TransactionLogDTO> {

    private final KafkaProducerService kafkaProducerService;

    public TransactionLogController(TransactionLogService transactionLogService, KafkaProducerService kafkaProducerService) {
        super(transactionLogService, TransactionLogDTO.class);
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public TransactionLogDTO insert(@RequestBody TransactionLogDTO transactionLogDTO) throws InterruptedException {
        transactionLogDTO = super.insert(transactionLogDTO);
        kafkaProducerService.sendMessage("TransactionLog",transactionLogDTO);
        return transactionLogDTO;
    }
}
