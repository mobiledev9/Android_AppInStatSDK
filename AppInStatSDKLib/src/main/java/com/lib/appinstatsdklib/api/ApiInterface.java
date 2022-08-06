package com.lib.appinstatsdklib.api;

import com.lib.appinstatsdklib.models.AppTrackerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface
{
    @GET("tracker/getTrackerDetailsByAuid")
    Call<AppTrackerResponse> GetTrackerDetailByID(@Query("auid")  String auid);
}
