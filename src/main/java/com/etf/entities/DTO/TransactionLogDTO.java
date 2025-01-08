package com.etf.entities.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TransactionLogDTO {
    private Integer id;
    private Integer transactionId;
    private Timestamp logTime;
    private Integer statusId;
    private Integer updatedByWorkerId;
}
