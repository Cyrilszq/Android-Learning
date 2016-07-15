package com.example.cyril.mvpdemo;

/**
 * Created by Cyril on 2016/7/15.
 */
public interface BaseView<T> {
    void setPresenter(T presenter);
    void showError();

}
