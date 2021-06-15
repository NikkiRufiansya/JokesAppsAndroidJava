package com.example.jokesappandroidjava.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JokesService {
    @GET("ten")
    Call<ResponseBody> getJokes();
}
