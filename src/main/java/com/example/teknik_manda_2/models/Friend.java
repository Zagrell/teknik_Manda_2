package com.example.teknik_manda_2.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
