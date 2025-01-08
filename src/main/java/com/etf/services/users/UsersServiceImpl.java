package com.etf.services.users;

import com.etf.base.BaseServiceImpl;
import com.etf.entities.Users;
import com.etf.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsersServiceImpl extends BaseServiceImpl<Users,Integer> implements UsersService {
    public UsersServiceImpl(UsersRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper, Users.class);
    }
}
