package com.example.jokesappandroidjava.helper;

import com.example.jokesappandroidjava.api.JokesService;

public class UrlApi {
    public static String BASE_URL = "https://official-joke-api.appspot.com/jokes/";

    public static JokesService getJokesService(){
        return RetrofitClient.getClient(BASE_URL).create(JokesService.class);
    }
}
