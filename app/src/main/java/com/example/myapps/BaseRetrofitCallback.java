package com.example.myapps;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseRetrofitCallback implements Callback<BaseResponseModel> {

    public static String TAG = BaseRetrofitCallback.class.getSimpleName();

    private BaseViewInterface view;
    private Context mContext;
    private SimpleCallback onSuccessCallback;

    public BaseRetrofitCallback(BaseViewInterface view, Context mContext, SimpleCallback<Response<BaseResponseModel>> onSuccessCallback){
        this.view = view;
        this.mContext = mContext;
        this.onSuccessCallback = onSuccessCallback;
    }

    @Override
    public void onResponse(Call<BaseResponseModel> call, Response<BaseResponseModel> response) {

        if (response.isSuccessful()) {
            if (response.body() != null) {
                if (Constants.TAG_OK.equalsIgnoreCase(response.body().getStatus())) {
                    onSuccessCallback.call(response);
                }
                else {
                    if (response.body() != null) {
                        view.showError(response.body().getArticles().toString());
                    }
                }
            }
        } else {
            try {
                if(Constants.STATUS_UNAUTHORIZED ==(response.code())){
                    view.showError(response.errorBody().string());
                    //new SessionManager(mContext).logoutUser();
                } else if (Constants.STATUS_INTERNAL_SERVER_ERROR == response.code()){
                    view.showError(response.errorBody().string());
                    Log.d(TAG, "internal server error");
                } else if(Constants.METHOD_NOT_ALLOWED == response.code()){
                    view.showError(response.errorBody().string());
                    Log.d(TAG, "Method Not Allowed");
                } else {
                    view.showError(response.errorBody().string());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<BaseResponseModel> call, Throwable t) {
        if (t instanceof TimeoutException) {
            view.showError(mContext.getResources().getString(R.string.error_try_again));
        } else if (t instanceof SocketTimeoutException) {
            view.showError(mContext.getResources().getString(R.string.error_try_again));
        } else {
            view.showError(t.getMessage());
        }
    }
}
