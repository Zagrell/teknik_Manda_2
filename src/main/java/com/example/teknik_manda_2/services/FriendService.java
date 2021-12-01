package com.example.teknik_manda_2.services;

import com.example.teknik_manda_2.models.Friend;
import com.example.teknik_manda_2.models.FriendRequest;
import com.example.teknik_manda_2.models.FriendResponse;
import com.example.teknik_manda_2.models.FriendUser;
import com.example.teknik_manda_2.repos.FriendRequestRepo;
import com.example.teknik_manda_2.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

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
    FriendUser foundUser;

    public String handleRequest(String[] splitBody) {
        sourceEmail = splitBody[1];
        sourceHost = splitBody[2];
        destEmail = splitBody[3];
        destHost = splitBody[4];
        version = splitBody[5];

        if (!destHost.equals(HOST)) {
            return new FriendResponse(FriendResponse.BAD_REQUEST).toString();
        }
        Optional<FriendUser> optionalUser = userRepo.findById(destEmail);
        if (optionalUser.isEmpty()) {
            return new FriendResponse(FriendResponse.NOT_FOUND).toString();
        }
        foundUser = optionalUser.get();


        switch (splitBody[0]) {

            case "add":
                return addFriend();

            case "accept":
                return receiveAcceptFriend();

            case "deny":
                return receiveDenyFriend();

            case "remove":
                return removeFriend();

            case "block":
                return blockFriend();
            default:
                return new FriendResponse(FriendResponse.BAD_REQUEST).toString();
        }
    }

    public String addFriend() {
        try {
            System.out.println("attempting to add friend");

            FriendRequest friendRequest = new FriendRequest(sourceEmail, sourceHost, foundUser);
            foundUser.addFriendRequest(friendRequest);
            friendRequestRepo.save(friendRequest);
            System.out.println(foundUser.getReceivedRequests());

            return new FriendResponse(FriendResponse.SUCCESS).toString();

        } catch (Exception e) {
            e.printStackTrace();
            return new FriendResponse(FriendResponse.BAD_REQUEST).toString();
        }
    }

    public String receiveAcceptFriend() {

        System.out.println("attempting to accept friend");
        FriendRequest foundRequest;

        try {
            foundRequest = foundUser.getSentFriendRequests().stream().filter(request ->
                    (request.getExternalEmail().equals(sourceEmail) &&
                            request.getExternalHost().equals(sourceHost))
            ).collect(Collectors.toList()).get(0);
        } catch (Exception e) {
            return new FriendResponse(FriendResponse.BAD_REQUEST).toString();
        }

        foundUser.addFriend(new Friend(sourceEmail, sourceHost));
        foundUser.removeSentRequest(foundRequest);

        return new FriendResponse(FriendResponse.SUCCESS).toString();
    }

    public String receiveDenyFriend() {

        System.out.println("attempting to deny friend");
        FriendRequest foundRequest;

        try {
            foundRequest = foundUser.getSentFriendRequests().stream().filter(request ->
                    (request.getExternalEmail().equals(sourceEmail) &&
                            request.getExternalHost().equals(sourceHost))
            ).collect(Collectors.toList()).get(0);
        } catch (Exception e) {
            return new FriendResponse(FriendResponse.BAD_REQUEST).toString();
        }

        foundUser.removeSentRequest(foundRequest);
        return new FriendResponse(FriendResponse.SUCCESS).toString();
    }

    public String removeFriend() {
        System.out.println("attempting to remove friend");

        Friend foundFriend;

        try {
            foundFriend = foundUser.getFriends().stream().filter(request ->
                    (request.getEmail().equals(sourceEmail) &&
                            request.getHost().equals(sourceHost))
            ).collect(Collectors.toList()).get(0);
        } catch (Exception e) {
            return new FriendResponse(FriendResponse.BAD_REQUEST).toString();
        }

        foundUser.removeFriend(foundFriend);

        return new FriendResponse(FriendResponse.SUCCESS).toString();
    }

    public String blockFriend() {
        return new FriendResponse(FriendResponse.SUCCESS).toString();
    }

}