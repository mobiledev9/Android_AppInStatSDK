package com.lib.appinstatsdklib.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppInfo
{
    @SerializedName("play_store_package_name")
    @Expose
    private String playStorePackageName;
    @SerializedName("app_store_app_id")
    @Expose
    private String appStoreAppId;

    public String getPlayStorePackageName() {
        return playStorePackageName;
    }

    public void setPlayStorePackageName(String playStorePackageName1) {
        this.playStorePackageName = playStorePackageName1;
    }

    public String getAppStoreAppId() {
        return appStoreAppId;
    }

    public void setAppStoreAppId(String appStoreAppId1) {
        this.appStoreAppId = appStoreAppId1;
    }

}
