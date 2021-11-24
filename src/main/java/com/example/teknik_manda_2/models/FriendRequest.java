package com.example.teknik_manda_2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "friend_request")
@Entity
public class FriendRequest {


    public FriendRequest(){

    }

    public FriendRequest(String sourceEmail, String sourceHost,FriendUser friendUser) {
        requestEmail = sourceEmail;
        requestHost = sourceHost;
        this.friendUser = friendUser;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "email", nullable = false)
    private FriendUser friendUser;

    @Column
    private String requestEmail;

    @Column
    private String requestHost;
}