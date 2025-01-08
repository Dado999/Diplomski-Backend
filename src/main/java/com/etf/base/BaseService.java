package com.etf.base;

import java.io.Serializable;
import java.util.List;

public interface BaseService<ID extends Serializable> {
    <T> List<T> findAll(Class<T> dtoClass);
    <T> T findByID(ID id, Class<T> resultDtoClass);
    <T,U> T insert(U object, Class<T> resultDtoClass);
    <T,U> T update(ID id,U object, Class<T> resultDtoClass);
    void delete(ID id);
}
