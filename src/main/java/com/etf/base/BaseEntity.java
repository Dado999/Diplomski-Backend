package com.etf.base;

import java.io.Serializable;

public interface BaseEntity<ID extends Serializable> {

    ID getId();
    void setId(ID id);
}
