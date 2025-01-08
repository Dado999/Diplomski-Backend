package com.etf.controllers;

import com.etf.base.BaseController;
import com.etf.entities.DTO.UsersDTO;
import com.etf.entities.DTO.WorkerDTO;
import com.etf.services.KafkaProducerService;
import com.etf.services.worker.WorkerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workers")
public class WorkerController extends BaseController<Integer, WorkerDTO> {

    private final KafkaProducerService kafkaProducerService;

    public WorkerController(WorkerService workerService, KafkaProducerService kafkaProducerService) {
        super(workerService,WorkerDTO.class);
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public WorkerDTO insert(@RequestBody WorkerDTO workerDTO) throws InterruptedException {
        workerDTO = super.insert(workerDTO);
        kafkaProducerService.sendMessage("Worker",workerDTO);
        return workerDTO;
    }
}
