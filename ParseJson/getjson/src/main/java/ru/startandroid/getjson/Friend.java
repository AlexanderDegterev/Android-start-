package ru.startandroid.getjson;


public class Friend {
    private Integer id;
    private String name;
    private String city;
    private Contacts contact;

    Friend(int id, String name, String city, Contacts contact){
        this.id = id;
        this.name = name;
        this.city = city;
        this.contact = contact;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String city) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

