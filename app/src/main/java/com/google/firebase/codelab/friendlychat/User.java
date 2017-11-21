package com.google.firebase.codelab.friendlychat;

/**
 * Created by edvin on 10/11/17.
 */
public class User
{
    private String userid;
    public  User() {

    }
    public User(String userid){
        this.userid = userid;
    }

    public String getId() {
        return userid;
    }
    public void setId(String chatid) {
        this.userid = userid;
    }

}
