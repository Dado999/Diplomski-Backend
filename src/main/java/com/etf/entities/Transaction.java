package com.etf.entities;

import com.etf.base.BaseEntity;
import com.etf.entities.DTO.TransactionDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Data
@Table(name = "transaction", schema = "diplomski")
public class Transaction implements BaseEntity<Integer>, Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Basic
    @Column(name = "created_date", nullable = true)
    private Timestamp createdDate;
    @Basic
    @Column(name = "from_account_id", nullable = false)
    private Integer fromAccountId;
    @Basic
    @Column(name = "to_account_id", nullable = false)
    private Integer toAccountId;
    @Basic
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(createdDate, that.createdDate) && Objects.equals(fromAccountId, that.fromAccountId) && Objects.equals(toAccountId, that.toAccountId) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, createdDate, fromAccountId, toAccountId, amount);
    }
}
