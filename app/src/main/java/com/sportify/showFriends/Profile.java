package com.sportify.showFriends;

import java.io.Serializable;

/**
 * Created by Maja on 2017-05-03.
 */


public class Profile implements Serializable {

    private String firstname;
    private String lastname;
    private String profilePicture;
    private int profileID;

    public Profile(String firstname, String lastname, String profilePicture, int profileID){
        this.firstname = firstname;
        this.lastname = lastname;
        this.profilePicture = profilePicture;
        this.profileID = profileID;

    }

    public String getFirstname(){
        return Character.toUpperCase(firstname.charAt(0)) + firstname.substring(1);
    }

    public String getLastname(){
        return Character.toUpperCase(lastname.charAt(0)) + lastname.substring(1);
    }

    public String getProfilePicture(){
        return profilePicture;
    }

    public int getProfileID() {
        return profileID;
    }

    public String toString(){
        return getFirstname() + " " + getLastname();
//                + " " + lastname.charAt(0) + " " + profileID;
    }
}
