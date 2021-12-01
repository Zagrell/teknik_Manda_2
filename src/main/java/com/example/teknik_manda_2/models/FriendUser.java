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
    private String host;

    @Column
    private String name;

    @OneToMany
    private List<FriendRequest> sentFriendRequests;

    @OneToMany
    private List<Friend> friends;

    @OneToMany(mappedBy = "ourUser", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<FriendRequest> receivedRequests;

    public void addFriendRequest(FriendRequest friendRequest) {
        receivedRequests.add(friendRequest);
    }

    public void addFriend(Friend friend) {
        friends.add(friend);
    }

    public void removeSentRequest(FriendRequest friendRequest) {
        sentFriendRequests.remove(friendRequest);
    }

    public void removeFriend(Friend friend) {
        friends.remove(friend);
    }
}
