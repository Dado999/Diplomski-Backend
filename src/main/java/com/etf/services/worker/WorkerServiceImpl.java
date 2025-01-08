package com.etf.services.worker;

import com.etf.base.BaseServiceImpl;
import com.etf.entities.Worker;
import com.etf.repositories.WorkerRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WorkerServiceImpl extends BaseServiceImpl<Worker,Integer> implements WorkerService {
    public WorkerServiceImpl(WorkerRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper, Worker.class);
    }
}
