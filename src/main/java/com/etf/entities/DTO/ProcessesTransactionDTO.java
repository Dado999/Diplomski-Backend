package com.etf.entities.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProcessesTransactionDTO {

    private TransactionDTO transactionDTO;
    private String totalAmount;
    private String averageAmount;
    private String origin;
}
