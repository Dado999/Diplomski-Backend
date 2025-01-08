package com.etf.services.account;

import com.etf.base.BaseServiceImpl;
import com.etf.entities.Account;
import com.etf.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountServiceImpl extends BaseServiceImpl<Account,Integer> implements AccountService {
    public AccountServiceImpl(AccountRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper, Account.class);
    }
}
