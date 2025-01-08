package com.etf.entities;

import com.etf.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Data
@Table(name = "account", schema = "diplomski")
public class Account implements BaseEntity<Integer>, Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Basic
    @Column(name = "account_type_id", nullable = false)
    private Integer accountTypeId;
    @Basic
    @Column(name = "account_number", nullable = false, length = 15)
    private String accountNumber;
    @Basic
    @Column(name = "bank_id", nullable = false)
    private Integer bankId;
    @Basic
    @Column(name = "balance", nullable = true)
    private BigDecimal balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(userId, account.userId) && Objects.equals(accountTypeId, account.accountTypeId) && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(bankId, account.bankId) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, accountTypeId, accountNumber, bankId, balance);
    }
}
