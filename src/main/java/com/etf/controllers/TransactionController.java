package com.etf.controllers;

import com.etf.base.BaseController;
import com.etf.entities.DTO.SettingsDTO;
import com.etf.entities.DTO.TransactionDTO;
import com.etf.services.ProcessedTransactionService;
import com.etf.services.transaction.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController extends BaseController<Integer, TransactionDTO> {

    private final TransactionService transactionService;
    private final ProcessedTransactionService processedTransactionService;

    public TransactionController(TransactionService transactionService,
                                 ProcessedTransactionService processedTransactionService) {
        super(transactionService, TransactionDTO.class);
        this.transactionService = transactionService;
        this.processedTransactionService = processedTransactionService;
    }

    @PostMapping("/generate")
    public void generate(@RequestBody SettingsDTO settingsDTO) throws InterruptedException, JsonProcessingException {
        transactionService.generateTransaction(settingsDTO.getTransactionNumber(),settingsDTO.getDuration());
    }

    @GetMapping("/sse")
    public SseEmitter streamTransactionEvents() {
        return processedTransactionService.addTransactionEmitter();
    }

}
