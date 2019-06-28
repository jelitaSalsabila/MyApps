package com.example.myapps;

public interface BaseViewInterface {
    void noInternetError();
    void showLoading();
    void dismissLoading();
    void hideKeyboard();
    void showError(String errorMessage);
}
