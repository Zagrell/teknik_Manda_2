package com.example.teknik_manda_2.repos;

import com.example.teknik_manda_2.models.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepo extends JpaRepository<Friend, Long> {
}
