package com.example.yongs.sharetrips.api.reports;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ReportApiService {
    @Multipart
    @POST("/reports")
    Call<ResponseBody> postReport(
            @PartMap() Map<String,Object> report,
            @Part MultipartBody.Part file
    );
}
