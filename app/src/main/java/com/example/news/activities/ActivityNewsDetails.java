package com.example.news.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.news.helpers.Constants;
import com.example.news.R;
import com.example.news.fragments.FragmentNewsDetails;

public class ActivityNewsDetails extends AppCompatActivity {

    public static void startActivity(Context context, String id) {
        Intent i = new Intent(context, ActivityNewsDetails.class);
        i.putExtra(Constants.NEWSID, id);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, FragmentNewsDetails.newInstance(), Constants.NEWSDETAILSFRAGMENT_TAG).commit();
        }
    }
}
