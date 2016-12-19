package com.example.news.Models;

/**
 * Created by Marianne on 17-Dec-16.
 */

public class NewsDetailsResponse {
    private NewsItem newsItem;

    public NewsItem getNewsItem() {
        return newsItem;
    }

    public void setNewsItem(NewsItem newsItem) {
        this.newsItem = newsItem;
    }
}
