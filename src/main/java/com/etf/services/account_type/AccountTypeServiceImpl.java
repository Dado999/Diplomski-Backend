package com.etf.services.account_type;

import com.etf.base.BaseServiceImpl;
import com.etf.entities.AccountType;
import com.etf.repositories.AccountTypeRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountTypeServiceImpl extends BaseServiceImpl<AccountType,Integer> implements AccountTypeService {
    public AccountTypeServiceImpl(AccountTypeRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper, AccountType.class);
    }
}
