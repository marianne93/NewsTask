package com.example.news.helpers;

import android.content.Context;

import com.example.news.Models.NewsDetailsResponse;
import com.example.news.Models.NewsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

/**
 * Created by Marianne.Wazif on 19-Dec-16.
 */

public class ParseNewsResponse {
    private Context mContext;
    private static ParseNewsResponse mInstance;
    private Gson mGson;
    String mNewsStr;

    private ParseNewsResponse(Context context) {
        mContext = context;

    }


    public static synchronized ParseNewsResponse getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ParseNewsResponse(context);
        }
        return mInstance;
    }


    public NewsResponse getNewsDataFromJson(JSONObject response) {

        mNewsStr = response.toString();
        mGson = new GsonBuilder().create();
        NewsResponse newsResponse = mGson.fromJson(mNewsStr, NewsResponse.class);
        return newsResponse;

    }

    public NewsDetailsResponse getNewsDetailsDataFromJson(JSONObject response) {

        mNewsStr = response.toString();
        mGson = new GsonBuilder().create();
        NewsDetailsResponse newsDetailsResponse = mGson.fromJson(mNewsStr, NewsDetailsResponse.class);
        return newsDetailsResponse;

    }
}
