package com.example.teknik_manda_2.controllers;

import com.example.teknik_manda_2.models.Friend;
import com.example.teknik_manda_2.models.FriendRequest;
import com.example.teknik_manda_2.models.FriendUser;
import com.example.teknik_manda_2.models.MethodRequest;
import com.example.teknik_manda_2.repos.FriendRepo;
import com.example.teknik_manda_2.repos.FriendRequestRepo;
import com.example.teknik_manda_2.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;

@Controller
public class FriendsController {


    @Autowired
    UserRepo userRepo;

    @Autowired
    FriendRepo friendRepo;

    @Autowired
    FriendRequestRepo friendRequestRepo;

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/")
    public String index(){
        return "html/index.html";
    }


    @PostMapping("/sendRequest")
    public String something(@ModelAttribute MethodRequest request, Model model){

        System.out.println(request);
        ResponseEntity<String> response = restTemplate.postForEntity(request.getDestHost()+"/friendship", request.toString(), String.class);
        if(request.getMethod().equals("add") && response.getBody().substring(2,5).equals("200")){
            System.out.println("hej");
            FriendUser friendUser = userRepo.getById(request.getSrcEmail());
            FriendRequest friendRequest = new FriendRequest(request.getDestEmail(), request.getDestHost(), friendUser);
            friendUser.addSentFriendRequest(friendRequest);
            friendRequestRepo.save(friendRequest);
            userRepo.save(friendUser);
        } else if (request.getMethod().equals("accept") && response.getBody().substring(2, 5).equals("200")) {
            System.out.print("accept");
            FriendUser friendUser = userRepo.getById(request.getSrcEmail());
            Friend friend = new Friend();
            friend.setHost(request.getDestHost());
            friend.setEmail(request.getDestEmail());
            friendUser.addFriend(friend);
            FriendRequest friendRequest = friendUser.findReceivedRequest(request.getDestEmail());
            friendUser.removeReceivedRequest(friendRequest);


            friendRepo.save(friend);
            friendRequestRepo.delete(friendRequest);
            userRepo.save(friendUser);
        }

        System.out.println(response.getBody());

        String email = "email=" + request.getSrcEmail();
        String host = "host=" + request.getSrcHost();

        System.out.print("html/user.html?" + email + "&" + host);

        return "html/user.html?" + email + "&" + host;
    }

    @PostMapping("/acceptRequest")
    public String antoherName(@ModelAttribute MethodRequest request) {
        System.out.print(request);

        return "html/acceptRequest.html";
    }




}
