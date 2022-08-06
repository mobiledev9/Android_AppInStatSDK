package com.lib.appinstatsdk;

import static com.lib.appinstatsdklib.models.AppConstant.TRACKER_DETAILS;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.lib.appinstatsdklib.AppInStatSdk;


public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppInStatSdk.getInstance().setEvent("login");
        AppInStatSdk.getInstance().setEvent("register");
    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }
}