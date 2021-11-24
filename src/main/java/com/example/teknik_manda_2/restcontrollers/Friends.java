package com.example.teknik_manda_2.restcontrollers;

import com.example.teknik_manda_2.models.FriendResponse;
import com.example.teknik_manda_2.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Friends {

    @Autowired
    FriendService friendService;

    @PostMapping("/friends")
    public String friendProtocol(@RequestBody String body) {

        System.out.println(body);
        String[] splitBody = body.toLowerCase().split(" ");
        switch (splitBody[0]) {
            case "add":
                return friendService.addFriend(splitBody);

            case "accept":
                return friendService.acceptFriend(splitBody);

            case "deny":
                return friendService.denyFriend(splitBody);

            case "remove":
                return friendService.removeFriend(splitBody);

            case "block":
                return friendService.blockFriend(splitBody);
            default:
                return new FriendResponse(FriendResponse.BAD_REQUEST).toString();
        }
    }



}
