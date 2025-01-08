package com.etf.entities.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer accountTypeId;
    private String accountNumber;
    private Integer bankId;
    private BigDecimal balance;
}
