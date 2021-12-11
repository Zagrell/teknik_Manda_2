package com.example.teknik_manda_2.services;

import com.example.teknik_manda_2.models.Friend;
import com.example.teknik_manda_2.models.FriendRequest;
import com.example.teknik_manda_2.models.FriendResponse;
import com.example.teknik_manda_2.models.FriendUser;
import com.example.teknik_manda_2.repos.FriendRepo;
import com.example.teknik_manda_2.repos.FriendRequestRepo;
import com.example.teknik_manda_2.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    FriendRequestRepo friendRequestRepo;

    @Autowired
    FriendRepo friendRepo;

    public static String host;

    @Autowired
    public void setHost(@Value("${hostname}") String host) {
        this.host = host;
    }

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

        if (!destHost.equals(host)) {
            return new FriendResponse(FriendResponse.BAD_REQUEST, "bad_host").toString();
        }
        Optional<FriendUser> optionalUser = userRepo.findById(destEmail);
        if (optionalUser.isEmpty()) {
            return new FriendResponse(FriendResponse.NOT_FOUND, "no_user_with_that_email").toString();
        }
        foundUser = optionalUser.get();


        return switch (splitBody[0]) {
            case "add" -> addFriend();
            case "accept" -> receiveAcceptFriend();
            case "deny" -> receiveDenyFriend();
            case "remove" -> removeFriend();
            case "block" -> blockFriend();
            default -> new FriendResponse(FriendResponse.BAD_REQUEST, "bad_method").toString();
        };
    }

    public String addFriend() {
        try {
            System.out.println("attempting to add friend");

            FriendRequest friendRequest = new FriendRequest(sourceEmail, sourceHost, foundUser);
            foundUser.addFriendRequest(friendRequest);
            friendRequestRepo.save(friendRequest);
            userRepo.save(foundUser);

            return new FriendResponse(FriendResponse.SUCCESS, "OK").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return new FriendResponse(FriendResponse.BAD_REQUEST, "OK").toString();
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
            return new FriendResponse(FriendResponse.BAD_REQUEST, "our_user_never_sent_friend_request").toString();
        }

        Friend friend = new Friend(sourceEmail, sourceHost);
        foundUser.addFriend(friend);
        foundUser.removeSentRequest(foundRequest);

        friendRepo.save(friend);
        userRepo.save(foundUser);

        return new FriendResponse(FriendResponse.SUCCESS, "OK").toString();
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
            return new FriendResponse(FriendResponse.BAD_REQUEST, "our_user_never_sent_friend_request").toString();
        }

        foundUser.removeSentRequest(foundRequest);

        userRepo.save(foundUser);
        return new FriendResponse(FriendResponse.SUCCESS, "OK").toString();
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
            return new FriendResponse(FriendResponse.BAD_REQUEST, "were_not_friends").toString();
        }

        foundUser.removeFriend(foundFriend);
        userRepo.save(foundUser);
        return new FriendResponse(FriendResponse.SUCCESS, "OK").toString();
    }

    public String blockFriend() {
        return new FriendResponse(FriendResponse.SUCCESS, "OK").toString();
    }

}