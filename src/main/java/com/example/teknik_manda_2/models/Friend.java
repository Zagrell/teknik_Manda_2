package com.example.teknik_manda_2.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Friend {

    @Id
    @Column
    private Long id;

    @Column
    private String email;

    @Column
    private String host;

    public Friend(String email, String host) {
        this.email = email;
        this.host = host;
    }

    public Friend() {

    }
}
