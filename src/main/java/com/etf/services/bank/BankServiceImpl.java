package com.etf.services.bank;

import com.etf.base.BaseServiceImpl;
import com.etf.entities.Bank;
import com.etf.repositories.BankRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankServiceImpl extends BaseServiceImpl<Bank,Integer> implements BankService {
    public BankServiceImpl(BankRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper, Bank.class);
    }
}
