package com.etf.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class ProcessesTransactionDTO {
    private TransactionDTO transactionDTO;
    private String totalAmount;
    private String averageAmount;
    private String origin;
    private Long elapsedTime;
}
