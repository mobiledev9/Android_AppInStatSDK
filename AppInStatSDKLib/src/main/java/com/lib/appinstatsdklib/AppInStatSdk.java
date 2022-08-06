package com.lib.appinstatsdklib;

import static com.lib.appinstatsdklib.models.AppConstant.TRACKER_DETAILS;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lib.appinstatsdklib.api.ApiManager;
import com.lib.appinstatsdklib.models.ApiResponseInterface;
import com.lib.appinstatsdklib.models.AppTrackerResponse;

import java.util.HashMap;

public class AppInStatSdk
{
    static Context context;
    static AppInStatSdk mInstance;
    static ApiResponseInterface objIntrface;
    static String appUnitID ;
    static FirebaseAnalytics mFirebaseAnalytics;

    private static final String PROPERTY_ID = "UA-62514259-2";

    public static int GENERAL_TRACKER = 0;

    public enum TrackerName {
        APP_TRACKER, GLOBAL_TRACKER, ECOMMERCE_TRACKER,
    }

    public static HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();


    public static synchronized Tracker getTracker(Context context , TrackerName appTracker) {
        if (!mTrackers.containsKey(appTracker)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
            Tracker t = (appTracker == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID) :
                    (appTracker == TrackerName.GLOBAL_TRACKER) ?
                            analytics.newTracker(R.xml.global_tracker) :

                            analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(appTracker, t);
        }
        return mTrackers.get(appTracker);
    }
    public static void  init (Context context1, String appUnitID1)
    {
        context = context1;
        appUnitID = appUnitID1;
        getInstance();

        Tracker t = getTracker(context , TrackerName.APP_TRACKER);
        t.setScreenName("Home");

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        objIntrface = new ApiResponseInterface() {
            @Override
            public void isError(String errorCode, int serviceCode, int modifiedPosition)
            {
                Toast.makeText(context, errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void isSuccess(Object response, int ServiceCode, int modifiedPosition)
            {
                switch(ServiceCode)
                {
                    case TRACKER_DETAILS:

                        Log.e("in main","calll");
                        AppTrackerResponse responseTrackerDetails = (AppTrackerResponse) response;
                        int statusTrakerAppDetails = responseTrackerDetails.getStatus();
                        if (statusTrakerAppDetails == 200)
                        {
                            Log.e("tracker info ", ""+responseTrackerDetails.getData().getTrackerInfo());
                            Log.e("app info ", ""+responseTrackerDetails.getData().getAppInfo());
                            Toast.makeText(context,  responseTrackerDetails.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(context,  responseTrackerDetails.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        break;
                }
            }
        };

        new ApiManager(context, objIntrface).makeTrackerDetailsByAuid(appUnitID);

    }

    public static AppInStatSdk getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new AppInStatSdk();
        }
        return mInstance;
    }


    public void setEvent(String eventName)
    {
        switch(eventName)
        {
            case "login" :
                Log.e("login call" , "call");
                break;

            case "register":
                Log.e("register call" , "call");
                break;
        }
    }


}
