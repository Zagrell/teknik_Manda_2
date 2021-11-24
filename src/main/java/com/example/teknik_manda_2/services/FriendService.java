package com.example.teknik_manda_2.services;

import com.example.teknik_manda_2.models.FriendRequest;
import com.example.teknik_manda_2.models.FriendResponse;
import com.example.teknik_manda_2.models.FriendUser;
import com.example.teknik_manda_2.repos.FriendRequestRepo;
import com.example.teknik_manda_2.repos.UserRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    FriendRequestRepo friendRequestRepo;

    public static final String HOST = "hostyhost";

    String sourceEmail;
    String sourceHost;
    String destEmail;
    String destHost;
    String version;

    private void setFields(String[] splitBody){
        sourceEmail = splitBody[1];
        sourceHost = splitBody[2];
        destEmail = splitBody[3];
        destHost = splitBody[4];
        version = splitBody[5];
    }

    public String addFriend(String[] splitBody) {
        try {
            System.out.println("atempting to add friend");
            setFields(splitBody);
            if(!destHost.equals(HOST)){
                return new FriendResponse(FriendResponse.BAD_REQUEST).toString();
            }
            Optional<FriendUser> optionalUser = userRepo.findById(destEmail);
            if(optionalUser.isEmpty()){
                return new FriendResponse(FriendResponse.NOT_FOUND).toString();
            }
            FriendUser foundUser = optionalUser.get();
            FriendRequest friendRequest = new FriendRequest(sourceEmail,sourceHost,foundUser);
            foundUser.addFriendRequest(friendRequest);
            friendRequestRepo.save(friendRequest);
            System.out.println(foundUser.getRequests());

            return new FriendResponse(FriendResponse.SUCCESS).toString();

        }catch (Exception e){
            e.printStackTrace();
            return new FriendResponse(FriendResponse.BAD_REQUEST).toString();
        }
    }

    public String acceptFriend(String[] splitBody) {



        return new FriendResponse(FriendResponse.SUCCESS).toString();
    }

    public String denyFriend(String[] splitBody) {
        return new FriendResponse(FriendResponse.SUCCESS).toString();
    }

    public String removeFriend(String[] splitBody) {
        return new FriendResponse(FriendResponse.SUCCESS).toString();
    }

    public String blockFriend(String[] splitBody) {
        return new FriendResponse(FriendResponse.SUCCESS).toString();
    }
}