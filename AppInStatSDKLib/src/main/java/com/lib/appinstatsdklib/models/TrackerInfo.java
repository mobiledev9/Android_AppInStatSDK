package com.lib.appinstatsdklib.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrackerInfo
{
    @SerializedName("tuid")
    @Expose
    private String tuid;
    @SerializedName("tracker_url")
    @Expose
    private String trackerUrl;
    @SerializedName("campaign_name")
    @Expose
    private String campaignName;
    @SerializedName("app_events")
    @Expose
    private List<String> appEvents = null;

    public String getTuid() {
        return tuid;
    }

    public void setTuid(String tuid) {
        this.tuid = tuid;
    }

    public String getTrackerUrl() {
        return trackerUrl;
    }

    public void setTrackerUrl(String trackerUrl) {
        this.trackerUrl = trackerUrl;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public List<String> getAppEvents() {
        return appEvents;
    }

    public void setAppEvents(List<String> appEvents) {
        this.appEvents = appEvents;
    }

}
