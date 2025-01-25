package com.etf.services.transaction;

import com.etf.base.BaseServiceImpl;
import com.etf.entities.DTO.Timestamps;
import com.etf.entities.DTO.TransactionDTO;
import com.etf.entities.DTO.TransactionLogDTO;
import com.etf.entities.DTO.TransactionStatusDTO;
import com.etf.entities.Transaction;
import com.etf.repositories.TransactionRepository;
import com.etf.services.KafkaProducerService;
import com.etf.services.transaction_log.TransactionLogService;
import com.etf.services.transaction_status.TransactionStatusService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import scala.Int;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class TransactionServiceImpl extends BaseServiceImpl<Transaction,Integer> implements TransactionService {

    private final TransactionStatusService transactionStatusService;
    private final TransactionLogService transactionLogService;
    private final KafkaProducerService kafkaProducerService;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TransactionServiceImpl(TransactionRepository repository, ModelMapper modelMapper, TransactionStatusService transactionStatusService, TransactionLogService transactionLogService, KafkaProducerService kafkaProducerService) {
        super(repository, modelMapper, Transaction.class);
        this.transactionStatusService = transactionStatusService;
        this.transactionLogService = transactionLogService;
        this.kafkaProducerService = kafkaProducerService;
    }

    public void generateTransaction(Integer transactionNumber, Integer duration) throws InterruptedException {
        for (int i = 0; i < transactionNumber; i++) {
            long sleep = duration * 10L;
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setAmount(BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(-1000, 1001)));
            transactionDTO.setCreatedDate(Timestamp.valueOf(LocalDateTime.now().format(dateTimeFormatter)));
            transactionDTO.setOrigin(getStatusOrigin());
            transactionDTO.setBeginningTimestamp(System.currentTimeMillis());
            kafkaProducerService.sendMessage("Transaction", UUID.randomUUID().toString(), transactionDTO);
            Thread.sleep(sleep);
        }
    }

    private String getStatusOrigin() {
        int value = new Random().nextInt(100);
        if (value < 10) {
            return "AUSTRALIA";
        } else if (value < 50) {
            return "EUROPE";
        } else if (value >= 60 && value < 80) {
            return "AMERICA";
        } else
            return "ASIA";
    }

}
