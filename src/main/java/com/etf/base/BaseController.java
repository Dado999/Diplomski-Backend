package com.etf.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@Data
public abstract class BaseController<ID extends Serializable, DTO> {

    public final Class<DTO> dtoClass;
    public final BaseService<ID> baseService;

    public BaseController(BaseService<ID> baseService, Class<DTO> dtoClass) {
        this.dtoClass = dtoClass;
        this.baseService = baseService;
    }

    @GetMapping
    public List<DTO> findAll() { return baseService.findAll(dtoClass); }

    @GetMapping("/{id}")
    public DTO findById(@PathVariable ID id) throws EntityNotFoundException { return baseService.findByID(id,dtoClass); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id) { baseService.delete(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DTO insert(@RequestBody DTO object) throws EntityNotFoundException, InterruptedException, JsonProcessingException { return baseService.insert(object,dtoClass); }

    @PutMapping("/{id}")
    public DTO update(@PathVariable ID id, @RequestBody DTO object) { return baseService.update(id,object,dtoClass); }

}
