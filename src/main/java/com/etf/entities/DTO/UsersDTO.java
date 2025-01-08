package com.etf.entities.DTO;

import lombok.Data;

@Data
public class UsersDTO {
    private Integer id;
    private Integer bankId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
