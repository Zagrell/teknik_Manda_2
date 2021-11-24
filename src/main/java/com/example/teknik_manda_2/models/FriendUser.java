package com.example.teknik_manda_2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@ToString(exclude = {"friends","requests"})
public class FriendUser {

    @Id
    @Column
    private String email;

    @Column
    private String name;

    @JsonIgnore
    @ManyToMany
    private List<FriendUser> friends;

    @JsonIgnore
    @OneToMany(mappedBy = "friendUser", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<FriendRequest> requests;

    public void addFriendRequest(FriendRequest friendRequest) {
        requests.add(friendRequest);
    }
}
