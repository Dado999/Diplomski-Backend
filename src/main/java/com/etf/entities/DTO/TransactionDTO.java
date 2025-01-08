package com.etf.entities.DTO;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Data
public class TransactionDTO implements Serializable {
    private Integer id;
    private Integer userId;
    private Timestamp createdDate = Timestamp.from(Instant.now());
    private Integer fromAccountId;
    private Integer toAccountId;
    private BigDecimal amount;

    public Double amountToDouble() { return Double.valueOf(String.valueOf(this.amount)); }
}
