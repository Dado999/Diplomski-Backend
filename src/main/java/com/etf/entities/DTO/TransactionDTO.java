package com.etf.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO implements Serializable {
    private Integer id;
    private Integer userId;
    private Timestamp createdDate = Timestamp.from(Instant.now());
    private Integer fromAccountId;
    private Integer toAccountId;
    private BigDecimal amount;
    private String origin = null;
}
