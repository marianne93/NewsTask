package com.example.news.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Marianne on 17-Dec-16.
 */

public class NewsItem {
    @SerializedName("NewsTitle")
    @Expose
    private String newsTitle;
    @SerializedName("ItemDescription")
    @Expose
    private String itemDescription;
    @SerializedName("Nid")
    @Expose
    private String nid;
    @SerializedName("NumofViews")
    @Expose
    private String numofViews;
    @SerializedName("Likes")
    @Expose
    private String likes;
    @SerializedName("PostDate")
    @Expose
    private String postDate;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("NewsType")
    @Expose
    private String newsType;
    @SerializedName("ShareURL")
    @Expose
    private String shareURL;
    @SerializedName("VideoURL")
    @Expose
    private String videoURL;


    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
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

    public String getShareURL() {
        return shareURL;
    }

    public void setShareURL(String shareURL) {
        this.shareURL = shareURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
