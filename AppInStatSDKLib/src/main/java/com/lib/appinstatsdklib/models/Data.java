package com.lib.appinstatsdklib.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data
{
    @SerializedName("app_info")
    @Expose
    private AppInfo appInfo;
    @SerializedName("tracker_info")
    @Expose
    private List<TrackerInfo> trackerInfo = null;

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public List<TrackerInfo> getTrackerInfo() {
        return trackerInfo;
    }

    public void setTrackerInfo(List<TrackerInfo> trackerInfo) {
        this.trackerInfo = trackerInfo;
    }
}
