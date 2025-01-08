package com.etf.services.transaction_status;

import com.etf.base.BaseServiceImpl;
import com.etf.entities.TransactionStatus;
import com.etf.repositories.TransactionStatusRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionStatusServiceImpl extends BaseServiceImpl<TransactionStatus,Integer> implements TransactionStatusService {
    public TransactionStatusServiceImpl(TransactionStatusRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper, TransactionStatus.class);
    }
}
