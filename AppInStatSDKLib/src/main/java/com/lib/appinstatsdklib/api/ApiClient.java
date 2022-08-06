package com.lib.appinstatsdklib.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient
{
    public static final String BASE_URL = "https://appinstat.com/api/";

    public static Retrofit getClient_withAuth(Context context)
    {
        OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(new AddCookiesInterceptor(context));
        builder.addInterceptor(new ReceivedCookiesInterceptor(context));
        client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }
    public static boolean isNetworkAvailable(Context con)
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static class AddCookiesInterceptor implements Interceptor
    {
        public static final String PREF_COOKIES = "PREF_COOKIES";
        private Context context;

        public AddCookiesInterceptor(Context context) {
            this.context = context;
        }

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException
        {
            Request.Builder builder = chain.request().newBuilder();

            HashSet<String> preferences = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(context)
                    .getStringSet(PREF_COOKIES, new HashSet<String>());

            for (String cookie : preferences)
            {
                builder.addHeader("Cookie", cookie);
            }
            return chain.proceed(builder.build());
        }
    }

    public static class ReceivedCookiesInterceptor implements Interceptor
    {
        private Context context;
        public ReceivedCookiesInterceptor(Context context)
        {
            this.context = context;
        }
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                HashSet<String> cookies = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(context).getStringSet("PREF_COOKIES", new HashSet<String>());

                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                }

                SharedPreferences.Editor memes = PreferenceManager.getDefaultSharedPreferences(context).edit();
                memes.putStringSet("PREF_COOKIES", cookies).apply();
                memes.commit();
            }

            return originalResponse;
        }
    }
}
