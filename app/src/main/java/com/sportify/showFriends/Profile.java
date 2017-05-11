package com.sportify.showFriends;

/**
 * Created by Maja on 2017-05-03.
 */


public class Profile {

    private String firstname;
    private String lastname;
    private int profilePicture;
    private int profileID;

    public Profile(String firstname, String lastname, int profilePicture, int profileID){
        this.firstname = firstname;
        this.lastname = lastname;
        this.profilePicture = profilePicture;
        this.profileID = profileID;
    }

    public String getFirstname(){
        return firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public int getProfilePicture(){
        return profilePicture;
    }

    public int getProfileID() {
        return profileID;
    }

    public String toString(){
        return firstname + " " + lastname.charAt(0) + " " + profileID;
    }
}
