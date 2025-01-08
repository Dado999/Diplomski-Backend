package com.etf.entities;

import com.etf.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Data
@Table(name = "transaction_log", schema = "diplomski", catalog = "diplomski")
public class TransactionLog implements BaseEntity<Integer>, Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "transaction_id", nullable = false)
    private Integer transactionId;
    @Basic
    @Column(name = "log_time", nullable = true)
    private Timestamp logTime;
    @Basic
    @Column(name = "status_id", nullable = false)
    private Integer statusId;
    @Basic
    @Column(name = "updated_by_worker_id", nullable = false)
    private Integer updatedByWorkerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionLog that = (TransactionLog) o;
        return Objects.equals(id, that.id) && Objects.equals(transactionId, that.transactionId) && Objects.equals(logTime, that.logTime) && Objects.equals(statusId, that.statusId) && Objects.equals(updatedByWorkerId, that.updatedByWorkerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionId, logTime, statusId, updatedByWorkerId);
    }
}
