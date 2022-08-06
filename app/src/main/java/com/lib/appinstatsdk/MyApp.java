package com.lib.appinstatsdk;

import android.app.Application;
import android.content.Context;

import com.lib.appinstatsdklib.AppInStatSdk;

public class MyApp extends Application
{
    public static String appUnitID = "45be69f3-9b1c-4029-a2c2-2880e6969f87";
    @Override
    public void onCreate()
    {
        super.onCreate();
        AppInStatSdk.init(this , appUnitID);
    }


}
