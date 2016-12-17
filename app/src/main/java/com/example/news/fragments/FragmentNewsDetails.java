package com.example.news.fragments;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.news.utils.NewsDetailsResponse;
import com.example.news.utils.NewsResponse;
import com.example.news.utils.VolleySingleton;
import com.example.news.R;
import com.example.news.activities.ActivityListingNews;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentNewsDetails extends Fragment {
    private ShareActionProvider mShareActionProvider;
    private static final String TAG = "DetailsRequestTag";
    private String mNewsID;
    private TextView mNewsTitle;
    private NetworkImageView mNewsImage;
    private TextView mNewsDate;
    private ImageView mNewsLikesIcon;
    private TextView mNewsLikes;
    private ImageView mNewsViewsIcon;
    private TextView mNewsViews;
    private TextView mNewsDescription;
    private ImageLoader mimageLoader;
    private String mShareURL;

    public FragmentNewsDetails() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mNewsID = getActivity().getIntent().getStringExtra(ActivityListingNews.NEWSID);
        // Toast.makeText(getActivity(), newsID, Toast.LENGTH_LONG).show();
        View rootView = inflater.inflate(R.layout.fragment_news_details, container, false);
        mNewsTitle = (TextView) rootView.findViewById(R.id.news_title);
        mNewsImage = (NetworkImageView) rootView.findViewById(R.id.news_image);
        mNewsDate = (TextView) rootView.findViewById(R.id.news_date);
        mNewsLikes = (TextView) rootView.findViewById(R.id.news_likes);
        mNewsLikesIcon = (ImageView) rootView.findViewById(R.id.news_likes_icon);
        mNewsViews = (TextView) rootView.findViewById(R.id.news_views);
        mNewsViewsIcon = (ImageView) rootView.findViewById(R.id.news_views_icon);
        mNewsDescription = (TextView) rootView.findViewById(R.id.news_description);


        return rootView;
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

                        try {
                            getNewsDetailsFromJson(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

    private void getNewsDetailsFromJson(JSONObject response) throws JSONException {
        String newsStr = response.toString();
        Gson gson = new GsonBuilder().create();
        NewsDetailsResponse newsDetailsResponse = gson.fromJson(newsStr, NewsDetailsResponse.class);
        mNewsTitle.setText(newsDetailsResponse.newsItem.NewsTitle);
        mimageLoader = VolleySingleton.getInstance(getActivity()).getImageLoader();
        mNewsImage.setImageUrl(newsDetailsResponse.newsItem.ImageUrl, mimageLoader);
        mNewsDate.setText(newsDetailsResponse.newsItem.PostDate);
        mNewsLikesIcon.setImageResource(R.drawable.likes);
        mNewsLikes.setText(getActivity().getResources().getString(R.string.likes) + " (" + newsDetailsResponse.newsItem.Likes + ")");
        mNewsViewsIcon.setImageResource(R.drawable.views_icon);
        mNewsViews.setText(newsDetailsResponse.newsItem.NumofViews + " " + getActivity().getResources().getString(R.string.views));
        mNewsDescription.setText(newsDetailsResponse.newsItem.ItemDescription);
        mShareURL = newsDetailsResponse.newsItem.ShareURL;
        mShareActionProvider.setShareIntent(createShareNewsIntent());



       /* final String NEWSITEM = "newsItem";
        final String NEWSTITLE = "NewsTitle";
        final String IMAGEURL = "ImageUrl";
        final String NUMOFVIEWS = "NumofViews";
        final String LIKES = "Likes";
        final String DESCRIPTION = "ItemDescription";
        final String SHAREURL = "ShareURL";
        final String POSTDATE = "PostDate";

        // TODO: bind Data to views
        JSONObject newsObj = response.getJSONObject(NEWSITEM);
        mNewsTitle.setText(newsObj.getString(NEWSTITLE));
        mimageLoader = VolleySingleton.getInstance(getActivity()).getImageLoader();
        mNewsImage.setImageUrl(newsObj.getString(IMAGEURL), mimageLoader);
        mNewsDate.setText(newsObj.getString(POSTDATE));
        mNewsLikesIcon.setImageResource(R.drawable.likes);
        mNewsLikes.setText(getActivity().getResources().getString(R.string.likes) + " (" + newsObj.getString(LIKES) + ")");
        mNewsViewsIcon.setImageResource(R.drawable.views_icon);
        mNewsViews.setText(newsObj.getString(NUMOFVIEWS) + " " + getActivity().getResources().getString(R.string.views));
        mNewsDescription.setText(newsObj.getString(DESCRIPTION));
        mShareURL = newsObj.getString(SHAREURL);
        mShareActionProvider.setShareIntent(createShareNewsIntent()); */

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_news_details, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);


        mShareActionProvider.setShareIntent(createShareNewsIntent());

    }

    private Intent createShareNewsIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mShareURL);
        return shareIntent;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (VolleySingleton.getInstance(getActivity()).getRequestQueue() != null) {
            VolleySingleton.getInstance(getActivity()).getRequestQueue().cancelAll(TAG);
        }
    }
}
