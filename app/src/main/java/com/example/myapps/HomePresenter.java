package com.example.myapps;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;

public class HomePresenter implements HomeInterface.Presenter {

    private BaseViewInterface baseView;
    private HomeInterface.View view;
    private Url url;
    private Context context;
    private Gson gson;

    public HomePresenter(MainActivity view, BaseViewInterface baseView,Context context) {
        this.baseView = baseView;
        this.url = RetrofitClientInstance.createService(Url.class) ;
        this.view = view;
        this.context = context;
        this.gson = new Gson();
    }
    @Override
    public void getArticles() {
        Call<BaseResponseModel> call = url.getJsonArticles();
        call.enqueue(new BaseRetrofitCallback(baseView,context,respond -> {
            JsonArray jsonArray = gson.toJsonTree(respond.body().getArticles()).getAsJsonArray();
            Type dataType = new TypeToken<Collection<ArticlesModel>>() {
            }.getType();
            List<ArticlesModel> list = gson.fromJson(jsonArray, dataType);
            view.setArticlesList(list);
            Log.d("sizeku", "getArticles: "+list);
        }));

    }

}
