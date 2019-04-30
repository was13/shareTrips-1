package com.example.yongs.sharetrips.api.users;

import com.example.yongs.sharetrips.model.User;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApiService {

    @GET("/users/{id}")
    Call<User> getUser(@Path("id") String id);

    @FormUrlEncoded
    @POST("/users/join")
    Call<User> postJoin(@FieldMap HashMap<String, Object> parameters);

    @FormUrlEncoded
    @POST("/users/login")
    Call<User> postLogin(@FieldMap HashMap<String, Object> parameters);

    @PATCH("/users/{id}/username")
    Call<Void> patchUsername(@Path("id") String id, @Body User user);

    @PATCH("/users/{id}/email")
    Call<Void> patchEmail(@Path("id") String id, @Body User user);

    @DELETE("/users/{id}")
    Call<Void> deleteUser(@Path("id") String id);

}
