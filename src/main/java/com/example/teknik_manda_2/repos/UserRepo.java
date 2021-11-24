package com.example.teknik_manda_2.repos;

import com.example.teknik_manda_2.models.FriendUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<FriendUser, String> {
}
