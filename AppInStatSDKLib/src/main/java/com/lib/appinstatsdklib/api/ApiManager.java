package com.lib.appinstatsdklib.api;

import static com.lib.appinstatsdklib.models.AppConstant.DEFAULT_MODIFIED_ID;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lib.appinstatsdklib.models.APIError;
import com.lib.appinstatsdklib.models.ApiResponseInterface;
import com.lib.appinstatsdklib.models.AppConstant;
import com.lib.appinstatsdklib.models.AppTrackerResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager
{
    public static Context mContext;
    public static ApiResponseInterface mApiResponseInterface;

    public  ApiManager(Context context, ApiResponseInterface apiResponseInterface)
    {
        this.mContext = context;
        this.mApiResponseInterface = apiResponseInterface;
    }

    public static void makeTrackerDetailsByAuid(String objCamp)
    {
        ApiInterface apiService = ApiClient.getClient_withAuth(mContext).create(ApiInterface.class);
        Call<AppTrackerResponse> call = apiService.GetTrackerDetailByID(objCamp);
        Log.e("call Tracker Details",""+call.request());

        call.enqueue(new Callback<AppTrackerResponse>()
        {
            @Override
            public void onResponse(Call<AppTrackerResponse> call, Response<AppTrackerResponse> response)
            {
                if(response != null && response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        Log.e("Tracker  response" , ""+ new Gson().toJson(response.body()));

                        Log.e("Tracker  response1111" , ""+ new Gson().toJson(response.body().getData()));

                        mApiResponseInterface.isSuccess(response.body(), AppConstant.TRACKER_DETAILS,
                                DEFAULT_MODIFIED_ID);
                    }
                }
                else
                {
                    try
                    {
                        String error = response.errorBody().string();
                        Log.e("error Tracker Details", ""+error);
                        Log.e("response is unsuccess", ""+response.code()+" "+response.errorBody().string());

                        if(error != null)
                        {
                            JsonParser parser = new JsonParser();
                            JsonElement mJson = null;
                            try
                            {
                                mJson = parser.parse(error);
                                Gson gson = new Gson();
                                APIError errorResponse = gson.fromJson(mJson, APIError.class);
                                Log.e(".....", ""+errorResponse.getMessage());
                                mApiResponseInterface.isError(errorResponse.getMessage(), AppConstant.TRACKER_DETAILS, DEFAULT_MODIFIED_ID);

                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                        else
                        {
                            mApiResponseInterface.isError("Something Wrong", AppConstant.TRACKER_DETAILS, DEFAULT_MODIFIED_ID);
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<AppTrackerResponse> call, Throwable t)
            {
                Log.e("Tracker exception",""+t.toString());
            }
        });
    }
}
