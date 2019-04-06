package com.example.yongs.sharetrips.api;

public interface ApiCallback<T> {
    void onError(Throwable t);

    void onSuccess(int code, T receiveData);

    void onFailure(int code);
}
