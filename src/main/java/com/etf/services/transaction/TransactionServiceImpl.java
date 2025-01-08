package com.etf.services.transaction;

import com.etf.base.BaseServiceImpl;
import com.etf.entities.Transaction;
import com.etf.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionServiceImpl extends BaseServiceImpl<Transaction,Integer> implements TransactionService {
    public TransactionServiceImpl(TransactionRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper, Transaction.class);
    }
}
