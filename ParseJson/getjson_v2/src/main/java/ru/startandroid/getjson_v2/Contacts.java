package ru.startandroid.getjson_v2;

public class Contacts {

    private String mobile;
    private String email;
    private String skype;

    Contacts(String mobile, String email, String skype) {
        this.mobile = mobile;
        this.email = email;
        this.skype = skype;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkype() {
        return this.skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }
}
