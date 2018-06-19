package com.desafiolatam.desafioface.views.splash;

import com.desafiolatam.desafioface.data.CurrentUsersQueries;

public class LoginValidator {

    private  LoginCallback callback;

    public LoginValidator(LoginCallback callback) {
        this.callback = callback;
    }
    public void init()
    {

        if(new CurrentUsersQueries().isLogged())
        {
            callback.signed();

        }
        else {
            callback.signUp();

        }
    }
}
