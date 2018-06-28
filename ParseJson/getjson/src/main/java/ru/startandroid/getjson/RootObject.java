package ru.startandroid.getjson;

import java.util.ArrayList;

public class RootObject {
    public String data;

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private ArrayList<Friend> friends;

    public ArrayList<Friend> getFriends() {
        return this.friends;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }
}
