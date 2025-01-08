package com.etf.services.transaction_log;

import com.etf.base.BaseServiceImpl;
import com.etf.entities.DTO.TransactionLogDTO;
import com.etf.entities.TransactionLog;
import com.etf.repositories.TransactionLogRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionLogServiceImpl extends BaseServiceImpl<TransactionLog,Integer> implements TransactionLogService {

    private final TransactionLogRepository transactionLogRepository;
    public TransactionLogServiceImpl(TransactionLogRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper, TransactionLog.class);
        this.transactionLogRepository = repository;
    }

    @Override
    public TransactionLogDTO findByTransactionId(Integer id) {
        TransactionLog transactionLog = transactionLogRepository.findByTransactionId(id).orElseThrow(null);
        return getModelMapper().map(transactionLog, TransactionLogDTO.class);
    }
}
