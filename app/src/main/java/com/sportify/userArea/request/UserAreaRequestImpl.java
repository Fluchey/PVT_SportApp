package com.sportify.userArea.request;

/**
 * Created by fluchey on 2017-04-20.
 */

public class UserAreaRequestImpl implements UserAreaRequest {
    private String token = "";

    public UserAreaRequestImpl(String token){ //Only added token for now.
        this.token = token;
    }
}
