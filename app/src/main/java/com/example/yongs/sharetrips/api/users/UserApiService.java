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

    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String username);

    @FormUrlEncoded
    @POST("/users/join")
    Call<User> postJoin(@FieldMap HashMap<String, Object> parameters);

    @FormUrlEncoded
    @POST("/users/login")
    Call<User> postLogin(@FieldMap HashMap<String, Object> parameters);

    @PUT("/users")
    Call<User> putUser(@Body User parameters);

    @FormUrlEncoded
    @PATCH("/users/{id}")
    Call<User> patchUsername(@Path("id") String id, @Field("username") String username);

    @FormUrlEncoded
    @PATCH("/users/{id}")
    Call<User> patchEmail(@Path("id") String id, @Field("email") String email);

    @DELETE("/users/{id}")
    Call<User> deleteUser(@Path("id") String id);

}
