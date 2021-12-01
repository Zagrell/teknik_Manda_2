package com.example.teknik_manda_2.models;

import lombok.Data;

@Data
public class FriendResponse {


    public static final int BAD_REQUEST = 402;
    public static final int SUCCESS = 200;
    public static final String VERSION = "0.1a";
    public static final int NOT_FOUND = 404;


    private int status;
    private String phrase;


    public FriendResponse() {
    }

    public FriendResponse(int status, String phrase){
        this.status = status;
        this.phrase = phrase;
    }

    @Override
    public String toString() {
        return "" + 1 + " " + status + " " + phrase + "\r\n";
    }
}
