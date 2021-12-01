package com.example.teknik_manda_2.models;


import lombok.Data;

@Data
public class MethodRequest {

    private String method;
    private String destEmail;
    private String destHost;
    private String srcEmail;
    private String srcHost;

    @Override
    public String toString(){
        return method + " " + srcEmail + " " + srcHost + " " + destEmail + " " + destHost + " " + 1 + "\r\n";
    }

}
