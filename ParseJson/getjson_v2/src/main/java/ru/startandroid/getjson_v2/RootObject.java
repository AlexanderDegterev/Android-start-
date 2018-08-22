package ru.startandroid.getjson_v2;

import java.util.ArrayList;

public class RootObject {
    private String data;
    private ArrayList<Friend> friends;

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setFriend(ArrayList<Friend> friends) {
        this.friends = friends;
    }
}
