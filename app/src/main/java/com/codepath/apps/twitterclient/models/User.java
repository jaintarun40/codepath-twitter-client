package com.codepath.apps.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private int id;
    private String userName;
    private String userProfileImage;
    private String userScreenName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public static User fromJson(JSONObject json) {
        User user = new User();
        try {
            user.id = json.getInt("id");
            user.userName = json.getString("name");
            user.userProfileImage = json.getString("profile_image_url");
            user.userScreenName = json.getString("screen_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

}
