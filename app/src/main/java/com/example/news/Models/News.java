package com.example.news.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by marianne.wazif on 13-Nov-16.
 */

public class News {
    @SerializedName("NewsTitle")
    @Expose
    private String newsTitle;
    @SerializedName("Nid")
    @Expose
    private String nid;
    @SerializedName("PostDate")
    @Expose
    private String postDate;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("NewsType")
    @Expose
    private String newsType;
    @SerializedName("NumofViews")
    @Expose
    private String numofViews;
    @SerializedName("Likes")
    @Expose
    private String likes;

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getNumofViews() {
        return numofViews;
    }

    public void setNumofViews(String numofViews) {
        this.numofViews = numofViews;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}

