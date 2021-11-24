package com.example.teknik_manda_2.repos;

import com.example.teknik_manda_2.models.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepo extends JpaRepository<FriendRequest, Integer> {
}
