package com.example.yongs.sharetrips.api;

import java.io.IOException;

public interface ApiCallback<T> {
    void onError(Throwable t);

    void onSuccess(int code, T receiveData);

    void onFailure(int code);
}
