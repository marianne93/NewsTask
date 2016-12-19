package com.example.news.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.news.Models.News;
import com.example.news.Models.NewsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Marianne on 02-Jul-16.
 */
public class Services {
    private static Services mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private  Context mContext;

    private final String BASE_URL =
            "http://egyptinnovate.com/en/api/v01/safe/";
    private  final String NEWS_URL = BASE_URL + "GetNews";
    private final String NEWS_DETAILS_URL =BASE_URL + "GetNewsDetails" ;
    private final String NEWS_PARAM = "nid";
   public enum Tag{
        NEWS,
       NEWS_DETAILS
   }


    private Services(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized Services getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Services(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void getNews( Response.Listener successListener,Response.ErrorListener errorListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(NEWS_URL, null,
                successListener,errorListener
        );
        jsonObjectRequest.setTag(Tag.NEWS);
        //Adding our request to the queue
        addToRequestQueue(jsonObjectRequest);
    }
    public void getNewsDetails(String newsID, Response.Listener successListener,Response.ErrorListener errorListener) {

        Uri builtUri = Uri.parse(NEWS_DETAILS_URL).buildUpon()
                .appendQueryParameter(NEWS_PARAM, newsID).build();
        final String DATA_URL = builtUri.toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(DATA_URL, null,
                successListener,errorListener
        );
        jsonObjectRequest.setTag(Tag.NEWS_DETAILS);
        //Adding our request to the queue
        addToRequestQueue(jsonObjectRequest);
    }
    public void getNewsTest(final Response.Listener<List<News>> successListener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(NEWS_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String newsStr = response.toString();
                        Gson gson = new GsonBuilder().create();
                        NewsResponse newsResponse = gson.fromJson(newsStr, NewsResponse.class);
                        successListener.onResponse(newsResponse.getNews());
                    }
                }, errorListener
        );
        jsonObjectRequest.setTag(Tag.NEWS);
        //Adding our request to the queue
        addToRequestQueue(jsonObjectRequest);
    }
}
