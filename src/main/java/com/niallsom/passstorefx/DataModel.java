package com.niallsom.passstorefx;

public class DataModel {
    private String website;
    private String email;
    private String password;

    public DataModel(String website, String email, String password) {
        this.website = website;
        this.email = email;
        this.password = password;
    }

    public String getWebsite() {
        return website;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
