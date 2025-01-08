package com.etf.controllers;

import com.etf.base.BaseController;
import com.etf.entities.DTO.TransactionLogDTO;
import com.etf.entities.DTO.TransactionStatusDTO;
import com.etf.services.KafkaProducerService;
import com.etf.services.transaction_status.TransactionStatusService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction-status")
public class TransactionStatusController extends BaseController<Integer, TransactionStatusDTO> {

    private final KafkaProducerService kafkaProducerService;

    public TransactionStatusController(TransactionStatusService transactionStatusService, KafkaProducerService kafkaProducerService) {
        super(transactionStatusService,TransactionStatusDTO.class);
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public TransactionStatusDTO insert(@RequestBody TransactionStatusDTO transactionStatusDTO) throws InterruptedException {
        transactionStatusDTO = super.insert(transactionStatusDTO);
        kafkaProducerService.sendMessage("TransactionStatus",transactionStatusDTO);
        return transactionStatusDTO;
    }
}
