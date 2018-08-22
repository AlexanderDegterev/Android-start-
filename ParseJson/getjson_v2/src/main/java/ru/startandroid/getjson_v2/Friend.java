package ru.startandroid.getjson_v2;


public class Friend {
    private Integer id;
    private String name;
    private String city;
    private Contacts contacts;

    Friend(int id, String name, String city, Contacts contacts){
        this.id = id;
        this.name = name;
        this.city = city;
        this.contacts = contacts;
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
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public Contacts getContacts() {
        return this.contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "[Friend: id=" + id + ", name=" + name + ", city=" + city + "]";
    }
}