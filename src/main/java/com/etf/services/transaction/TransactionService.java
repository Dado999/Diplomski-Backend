package com.etf.services.transaction;

import com.etf.base.BaseService;

public interface TransactionService extends BaseService<Integer> {
    public void generateTransaction(Integer transactionNumber, Integer duration) throws InterruptedException;
}
