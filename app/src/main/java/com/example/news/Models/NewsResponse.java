package com.example.news.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Marianne on 17-Dec-16.
 */

public class NewsResponse {
    @SerializedName("News")
    @Expose
    private List<News> news = null;

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }


   /* private List<News> News;

    public List<com.example.news.Models.News> getNews() {
        return News;
    }

    public void setNews(List<com.example.news.Models.News> news) {
        News = news;
    } */
}
