package com.example.yongs.sharetrips.api.users;

import android.content.Context;

import com.example.yongs.sharetrips.api.ApiCallback;
import com.example.yongs.sharetrips.common.Common;
import com.example.yongs.sharetrips.model.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUsers {

        private UserApiService mUserApiService;
        public static String mBaseUrl = Common.BaseURL;
        private static Context mContext;
        private static Retrofit mRetrofit;

        private RetrofitUsers(Context context){
            mRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(mBaseUrl)
                    .build();
        }

        public static RetrofitUsers getInstance(Context context){
            if(context != null){
                mContext = context;
            }
            return new RetrofitUsers(mContext);
        }

        public RetrofitUsers createBaseApi(){
            mUserApiService = create(UserApiService.class);
            return this;
        }

        public <T>T create(final Class<T> service){
            if(service == null){
                throw new RuntimeException("Api service is null!");
            }
            return mRetrofit.create(service);
        }

        public void getUser(String id, final ApiCallback callback) {
            mUserApiService.getUser(id).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        callback.onSuccess(response.code(), response.body());
                    }else{
                        callback.onFailure(response.code());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    callback.onError(t);
                }
            });
        }

        public void postJoin(HashMap<String, Object> parameters, final ApiCallback callback) {
            mUserApiService.postJoin(parameters).enqueue(new Callback<User>(){

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        callback.onSuccess(response.code(),response.body());
                    }else{
                        callback.onFailure(response.code());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    callback.onError(t);
                }
            });
        }

        public void postLogin(HashMap<String, Object> parameters, final ApiCallback callback) {
            mUserApiService.postLogin(parameters).enqueue(new Callback<User>(){

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        callback.onSuccess(response.code(),response.body());
                    }else{
                        callback.onFailure(response.code());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    callback.onError(t);
                }
            });
        }

        public void putUser(HashMap<String, Object> parameters, final ApiCallback callback) {
            mUserApiService.putUser(new User(parameters)).enqueue(new Callback<User>(){

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        callback.onSuccess(response.code(),response.body());
                    }else{
                        callback.onFailure(response.code());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    callback.onError(t);
                }
            });
        }

        public void patchUsername(String id,String username, final ApiCallback callback) {
            mUserApiService.patchUsername(id,username).enqueue(new Callback<User>(){

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        callback.onSuccess(response.code(),response.body());
                    }else{
                        callback.onFailure(response.code());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    callback.onError(t);
                }
            });
        }

        public void patchEmail(String id,String email, final ApiCallback callback) {
            mUserApiService.patchEmail(id,email).enqueue(new Callback<User>(){

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        callback.onSuccess(response.code(),response.body());
                    }else{
                        callback.onFailure(response.code());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    callback.onError(t);
                }
            });
        }

        public void deleteUser(String id, final ApiCallback callback) {
            mUserApiService.deleteUser(id).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        callback.onSuccess(response.code(),response.body());
                    }else{
                        callback.onFailure(response.code());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    callback.onError(t);
                }
            });
        }
}
