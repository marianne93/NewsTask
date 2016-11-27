package com.example.news;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.net.FileNameMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsDetailsFragment extends Fragment {
    private ShareActionProvider mShareActionProvider;
    private static final String TAG = "DetailsRequestTag";
    private String mNewsID;
    public NewsDetailsFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mNewsID = getActivity().getIntent().getStringExtra(MainActivity.NEWSID);
       // Toast.makeText(getActivity(), newsID, Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_news_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GetNewsDetails();
    }

    private void GetNewsDetails() {
        final String NEWS_BASE_URL =
                "http://egyptinnovate.com/en/api/v01/safe/GetNewsDetails";
        final String NEWS_PARAM = "nid";
        Uri builtUri = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter(NEWS_PARAM, mNewsID).build();
        final String DATA_URL = builtUri.toString();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(DATA_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        getNewsDetailsFromJson(response);

                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_LONG).show();

                    }
                }
        );
        jsonObjectRequest.setTag(TAG);


        //Adding our request to the queue
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

    private void getNewsDetailsFromJson(JSONObject response) {
        final String NEWSTITLE = "NewsTitle";
        final String IMAGEURL = "ImageUrl";
        final String NUMOFVIEWS = "NumofViews";
        final String LIKES = "Likes";
        final String DESCRIPTION = "ItemDescription";
        final String SHAREURL = "ShareURL";

        // TODO: bind Data to views


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_news_details, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);


        mShareActionProvider.setShareIntent(createShareForecastIntent());

    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        //    shareIntent.putExtra(Intent.EXTRA_TEXT, mForecast + FORECAST_SHARE_HASHTAG);
        return shareIntent;
    }

}
