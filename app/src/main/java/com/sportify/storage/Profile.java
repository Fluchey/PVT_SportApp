package com.sportify.storage;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rasmu on 25/05/2017.
 */

public class Profile {

    private String firstname;
    private String lastname;
    private String description;
    private List<String> interests;

    private int id;
    private int age;

    private String picture;

    public Profile(String firstname, String lastname, String description, List<String> interests, int id, int age, String picture){
        this.firstname = firstname;
        this.lastname = lastname;
        this.description = description;
        this.interests = interests;
        this.id = id;
        this.age = age;
        this.picture = picture;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getInterests() {
        return interests;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getPicture() {
        return picture;
    }
}
