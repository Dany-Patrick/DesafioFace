package com.desafiolatam.desafioface.networks.sessions;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginInterceptor {
    public static final String BASE_URL = "https://empieza.desafiolatam.com/";

    public Session get()
    {
        Retrofit interceptor = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                /*Never forget about adding the converter, otherwise you can not parse the data*/
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Session session = interceptor.create(Session.class);
        return session;
    }
}
