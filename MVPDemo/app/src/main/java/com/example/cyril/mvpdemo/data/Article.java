package com.example.cyril.mvpdemo.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Cyril on 2016/7/15.
 */
public class Article {
    @SerializedName("error")
    public boolean error;

    @SerializedName("results")
    public List<ResultsBean> results;

    public static class ResultsBean {


        @SerializedName("_id")
        public String id;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("desc")
        public String desc;
        @SerializedName("publishedAt")
        public String publishedAt;
        @SerializedName("source")
        public String source;
        @SerializedName("type")
        public String type;
        @SerializedName("url")
        public String url;
        @SerializedName("used")
        public boolean used;
        @SerializedName("who")
        public String who;

        public boolean isRead = false;
    }
}
