package com.example.yongs.sharetrips.api.reports;

import com.example.yongs.sharetrips.model.Report;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ReportApiService {

    @GET("/reports/{username}")
    Call<Report> getReport(@Path("username") String username);


    @GET("/reports/images/{username}")
    Call<ResponseBody> getImage(@Path("username") String username);

    @Multipart
    @POST("/reports")
    Call<ResponseBody> postReport(
            @PartMap() Map<String,Object> report,
            @Part MultipartBody.Part file
    );
}
