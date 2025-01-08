package com.etf.services.transaction_log;

import com.etf.base.BaseService;
import com.etf.entities.DTO.TransactionLogDTO;

public interface TransactionLogService extends BaseService<Integer> {

    TransactionLogDTO findByTransactionId(Integer id);
}
