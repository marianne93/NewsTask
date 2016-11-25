package com.example.news;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsDetailsFragment extends Fragment {

    public NewsDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String newsID = getActivity().getIntent().getStringExtra(MainActivity.NEWSID);
        Toast.makeText(getActivity(),newsID,Toast.LENGTH_LONG ).show();
        return inflater.inflate(R.layout.fragment_news_details, container, false);
    }

}
