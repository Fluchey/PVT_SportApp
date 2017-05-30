package com.sportify.storage;

/**
 * Created by Maja on 2017-05-30.
 */

public class Participant extends com.sportify.showFriends.Profile{

    String status;

    public Participant(String firstname, String lastname, String profilePicture, int profileID, String status) {
        super(firstname, lastname, profilePicture, profileID);
        this.status = status;
    }

    public String getStatus(){

        if(status.equalsIgnoreCase("attending")){
            return "Kommer";
        }else if(status.equalsIgnoreCase("interested")){
            return "Intresserad";
        }else if(status.equalsIgnoreCase("invited")){
            return "Inbjuden";
        }else if(status.equalsIgnoreCase("not_attending")){
            return "Kommer inte";
        }

        return null;
    }
}
