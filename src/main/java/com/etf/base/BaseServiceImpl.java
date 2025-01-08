package com.etf.base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Transactional
public class BaseServiceImpl<E extends BaseEntity<ID>,ID extends Serializable> implements BaseService<ID> {

    private final JpaRepository<E,ID> repository;
    private final ModelMapper modelMapper;
    private final Class<E> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

    public BaseServiceImpl(JpaRepository<E, ID> repository,ModelMapper modelMapper, Class<E> entityClass) {
        this.repository = repository;
        this.entityClass = entityClass;
        this.modelMapper = modelMapper;
    }
    @Override
    public <T> List<T> findAll(Class<T> dtoClass) {
        return repository.findAll().stream().map(e -> modelMapper.map(e,dtoClass)).collect(Collectors.toList());
    }
    @Override
    public <T> T findByID(ID id, Class<T> resultDtoClass) {
        return modelMapper.map(findEntityByID(id),resultDtoClass);
    }
    private E  findEntityByID(ID id){
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    @Override
    public <T, U> T insert(U object, Class<T> resultDtoClass) {
        E entity = modelMapper.map(object,entityClass);
        entity.setId(null);
        entity = repository.saveAndFlush(entity);
        entityManager.refresh(entity);
        return modelMapper.map(entity,resultDtoClass);
    }
    @Override
    public <T, U> T update(ID id, U object, Class<T> resultDtoClass) {
        if(!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        E entity = modelMapper.map(object,entityClass);
        entity.setId(id);
        entity = repository.saveAndFlush(entity);
        entityManager.refresh(entity);
        return modelMapper.map(entity,resultDtoClass);
    }
    @Override
    public void delete(ID id) {
        if(!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        repository.deleteById(id);
    }
}
