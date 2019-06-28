package com.example.myapps;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Url {

    @GET("everything?q=bitcoin&from=2019-05-18&sortBy=publishedAt&apiKey=3fbed3b7a6724abea2dfd7295fe1a4e5")
    Call<BaseResponseModel> getJsonArticles();
}
