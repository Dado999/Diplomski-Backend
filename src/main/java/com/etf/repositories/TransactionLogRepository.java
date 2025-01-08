package com.etf.repositories;

import com.etf.entities.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog,Integer> {
    Optional<TransactionLog> findByTransactionId(Integer integer);
}
