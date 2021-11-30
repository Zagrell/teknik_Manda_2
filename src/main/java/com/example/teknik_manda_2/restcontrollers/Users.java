package com.example.teknik_manda_2.restcontrollers;

import com.example.teknik_manda_2.models.FriendUser;
import com.example.teknik_manda_2.repos.UserRepo;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Users {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/users")
    public List<FriendUser> getUser() {
        return userRepo.findAll();
    }

    @GetMapping("/users/{email}")
    public FriendUser getUserById(@PathVariable String email) {
        return userRepo.findById(email).get();
    }

    @PostMapping("/users")
    public FriendUser addUser(@RequestBody FriendUser newFriendUser) {
        return userRepo.save(newFriendUser);
    }

    @PostMapping("/users/{email}")
    public FriendUser addFriendUser(@PathVariable String email, @RequestBody FriendUser newFriendUser) {
        return userRepo.save(newFriendUser);
    }

    @PatchMapping("/users/{email}")
    public String patchFriendUserById(@PathVariable String email, @RequestBody FriendUser friendUserToUpdateWith) {
        return userRepo.findById(email).map(foundUser -> {
            if (friendUserToUpdateWith.getName() != null) foundUser.setName(friendUserToUpdateWith.getName());

            userRepo.save(foundUser);
            return "User was updated";
        }).orElse("User was not found");
    }

    @DeleteMapping("/users/{email}")
    public void deleteFriendUserByEmail(@PathVariable String email) {
        userRepo.deleteById(email);
    }

}
