package com.example.myapps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class BaseResponseModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("articles")
    @Expose
    private Object articles;

    public String getStatus() { return status; }

//    public Object getResult() {
//        return result;
//    }

    public Object getArticles(){
        return articles;
    }


}
