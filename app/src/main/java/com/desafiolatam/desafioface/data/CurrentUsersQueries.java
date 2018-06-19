package com.desafiolatam.desafioface.data;

import com.desafiolatam.desafioface.models.CurrentUser;

public class CurrentUsersQueries {
    public boolean isLogged()
    {
        return CurrentUser.listAll(CurrentUser.class).size() > 0;
    }
    public CurrentUser get()
    {
        return CurrentUser.listAll(CurrentUser.class).get(0);
    }
}
