package com.sportify.showFriends;

/**
 * Created by Maja on 2017-05-03.
 */


//Test
public class Profile {

    private String firstname;
    private String lastname;
    private int profilePicture;

    public Profile(String firstname, String lastname, int profilePicture){
        this.firstname = firstname;
        this.lastname = lastname;
        this.profilePicture = profilePicture;
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

    public String toString(){
        return firstname;
    }
}
