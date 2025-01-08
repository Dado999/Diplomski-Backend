package com.etf.entities;

import com.etf.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@Table(name = "bank", schema = "diplomski")
public class Bank implements BaseEntity<Integer>, Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = true, length = 45)
    private String name;
    @Basic
    @Column(name = "location", nullable = true, length = 45)
    private String location;
    @Basic
    @Column(name = "address", nullable = true, length = 45)
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(id, bank.id) && Objects.equals(name, bank.name) && Objects.equals(location, bank.location) && Objects.equals(address, bank.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location, address);
    }
}
