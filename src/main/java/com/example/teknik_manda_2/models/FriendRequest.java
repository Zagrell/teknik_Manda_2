package com.example.teknik_manda_2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Table(name = "friend_request")
@Entity
public class FriendRequest {


    public FriendRequest(){

    }

    public FriendRequest(String sourceEmail, String sourceHost,FriendUser ourUser) {
        externalEmail = sourceEmail;
        externalHost = sourceHost;
        this.ourUser = ourUser;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    private FriendUser ourUser;

    @Column
    private String externalEmail;

    @Column
    private String externalHost;

}