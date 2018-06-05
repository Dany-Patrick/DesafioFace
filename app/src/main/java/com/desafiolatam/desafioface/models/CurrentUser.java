package com.desafiolatam.desafioface.models;

public class CurrentUser extends SugarBase{
    private String name,photo_url,auth_token,email;


    public CurrentUser() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhoto_url() {
        return this.photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getAuth_token() {
        return this.auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
