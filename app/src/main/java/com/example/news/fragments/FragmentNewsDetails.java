package com.example.news.fragments;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.news.helpers.Constants;
import com.example.news.helpers.ParseNewsResponse;
import com.example.news.Models.NewsDetailsResponse;
import com.example.news.helpers.Utility;
import com.example.news.helpers.Services;
import com.example.news.R;

import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentNewsDetails extends Fragment {
    private ShareActionProvider mShareActionProvider;

    private String mNewsID;
    private TextView mNewsTitle;
    private NetworkImageView mNewsImage;
    private TextView mNewsDate;

    private TextView mNewsLikes;

    private TextView mNewsViews;
    private TextView mNewsDescription;
    private ImageLoader mimageLoader;
    private String mShareURL;
    private ProgressBar mProgressBar;
    private ScrollView scrlNewsDetails;

    public static FragmentNewsDetails newInstance() {
        return new FragmentNewsDetails();
    }

    public FragmentNewsDetails() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mNewsID = getActivity().getIntent().getStringExtra(Constants.NEWSID);
        // Toast.makeText(getActivity(), newsID, Toast.LENGTH_LONG).show();
        View rootView = inflater.inflate(R.layout.fragment_news_details, container, false);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        mNewsTitle = (TextView) rootView.findViewById(R.id.news_title);
        mNewsImage = (NetworkImageView) rootView.findViewById(R.id.news_image);
        mNewsDate = (TextView) rootView.findViewById(R.id.news_date);
        mNewsLikes = (TextView) rootView.findViewById(R.id.news_likes);

        mNewsViews = (TextView) rootView.findViewById(R.id.news_views);

        mNewsDescription = (TextView) rootView.findViewById(R.id.news_description);
        scrlNewsDetails = (ScrollView) rootView.findViewById(R.id.scrlNewsDetails);

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Utility.HaveNetworkConnection(getActivity())) {
            GetNewsDetails();
        } else {
            Toast.makeText(getActivity(), "There is no internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void GetNewsDetails() {
        Services.getInstance(getActivity()).getNewsDetails(mNewsID, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                getNewsDetailsFromJson(response);

                mProgressBar.setVisibility(View.GONE);
                scrlNewsDetails.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getNewsDetailsFromJson(JSONObject response) {
        NewsDetailsResponse newsDetailsResponse = ParseNewsResponse.getInstance(getActivity()).getNewsDetailsDataFromJson(response);
        mNewsTitle.setText(newsDetailsResponse.getNewsItem().getNewsTitle());
        mimageLoader = Services.getInstance(getActivity()).getImageLoader();
        mNewsImage.setImageUrl(newsDetailsResponse.getNewsItem().getImageUrl(), mimageLoader);
        mNewsDate.setText(newsDetailsResponse.getNewsItem().getPostDate());

        mNewsLikes.setText(getActivity().getResources().getString(R.string.likes) + " (" + newsDetailsResponse.getNewsItem().getLikes() + ")");

        mNewsViews.setText(newsDetailsResponse.getNewsItem().getNumofViews() + " " + getActivity().getResources().getString(R.string.views));
        mNewsDescription.setText(newsDetailsResponse.getNewsItem().getItemDescription());
        mShareURL = newsDetailsResponse.getNewsItem().getShareURL();
        mShareActionProvider.setShareIntent(createShareNewsIntent());

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
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mShareURL);
        return shareIntent;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Services.getInstance(getActivity()).getRequestQueue() != null) {
            Services.getInstance(getActivity()).getRequestQueue().cancelAll(Constants.DETAILSTAG);
        }
    }
}
