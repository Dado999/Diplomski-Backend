package com.etf.controllers;

import com.etf.base.BaseController;
import com.etf.entities.DTO.TransactionDTO;
import com.etf.entities.DTO.TransactionLogDTO;
import com.etf.entities.DTO.TransactionStatusDTO;
import com.etf.services.KafkaProducerService;
import com.etf.services.ProcessedTransactionService;
import com.etf.services.transaction.TransactionService;
import com.etf.services.transaction_log.TransactionLogService;
import com.etf.services.transaction_status.TransactionStatusService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController extends BaseController<Integer, TransactionDTO> {

    private final KafkaProducerService kafkaProducerService;
    private final ProcessedTransactionService processedTransactionService;
    private final TransactionLogService transactionLogService;
    private final TransactionStatusService transactionStatusService;

    public TransactionController(TransactionService transactionService,
                                 KafkaProducerService kafkaProducerService,
                                 ProcessedTransactionService processedTransactionService,
                                 TransactionLogService transactionLogService, TransactionStatusService transactionStatusService) {
        super(transactionService, TransactionDTO.class);
        this.kafkaProducerService = kafkaProducerService;
        this.processedTransactionService = processedTransactionService;
        this.transactionLogService = transactionLogService;
        this.transactionStatusService = transactionStatusService;
    }

    @Override
    public TransactionDTO insert(@RequestBody TransactionDTO transactionDTO) throws InterruptedException, JsonProcessingException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        transactionDTO = super.insert(transactionDTO);
        transactionDTO.setOrigin(getStatusOrigin(createNewTransactionLog(transactionDTO)));
        kafkaProducerService.sendMessage("Transaction","global", transactionDTO);

        for (int i = 0; i < 20; i++) {
            int minValue = Integer.parseInt(System.getProperty("application.lower-limit", "-1000"));
            int maxValue = Integer.parseInt(System.getProperty("application.upper-limit", "1000"));
            TransactionDTO transactionDTO1 = new TransactionDTO();
            transactionDTO1.setUserId(3);
            transactionDTO1.setFromAccountId(5);
            transactionDTO1.setToAccountId(6);
            transactionDTO1.setAmount(BigDecimal.valueOf(new Random().nextInt(minValue, maxValue)));
            String randomDate = generateRandomTimestamp(dateTimeFormatter);
            transactionDTO1.setCreatedDate(Timestamp.valueOf(randomDate));
            transactionDTO1 = super.insert(transactionDTO1);
            transactionDTO1.setOrigin(getStatusOrigin(createNewTransactionLog(transactionDTO)));
            kafkaProducerService.sendMessage("Transaction","global", transactionDTO1);
            Thread.sleep(700);
        }

        return transactionDTO;
    }

    private String generateRandomTimestamp(DateTimeFormatter formatter) {
        Random random = new Random();

        int year = random.nextInt(2020, 2021);
        int month = random.nextInt(1, 6);
        int day = random.nextInt(1, 3);
        int hour = random.nextInt(0, 24);
        int minute = random.nextInt(0, 60);
        int second = random.nextInt(0, 60);
        LocalDateTime randomDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
        return randomDateTime.format(formatter);
    }

    private TransactionLogDTO createNewTransactionLog(TransactionDTO transactionDTO){
        TransactionLogDTO transactionLogDTO = new TransactionLogDTO();
        transactionLogDTO.setTransactionId(transactionDTO.getId());
        transactionLogDTO.setLogTime(Timestamp.from(Instant.now()));
        transactionLogDTO.setStatusId(new Random().nextInt(1,5));
        transactionLogDTO.setUpdatedByWorkerId(1);
        transactionLogDTO = transactionLogService.insert(transactionLogDTO, TransactionLogDTO.class);
        kafkaProducerService.sendMessage("TransactionLog", "global", transactionLogDTO);
        return transactionLogDTO;
    }

    @GetMapping("/sse")
    public SseEmitter streamTransactionEvents() {
        return processedTransactionService.addTransactionEmitter();
    }


    private String getStatusOrigin(TransactionLogDTO transactionStatusDTO) {
        return transactionStatusService.findByID(transactionStatusDTO.getStatusId(), TransactionStatusDTO.class).getCode();
    }
}
