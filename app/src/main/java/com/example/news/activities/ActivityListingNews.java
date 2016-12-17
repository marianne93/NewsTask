package com.example.news.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.news.adapters.DrawerItemsAdapter;
import com.example.news.fragments.FragmentListingNews;
import com.example.news.R;
import com.example.news.utils.News;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityListingNews extends AppCompatActivity
        implements FragmentListingNews.OnListFragmentInteractionListener {
    private DrawerLayout mdrawer;
    private RecyclerView mDrawerList;
    private ArrayList<String> mDrawerItemsTitles = new ArrayList<String>();
    private DrawerItemsAdapter drawerItemsAdapter;
    private static final String NEWSLISTINGFRAGMENT_TAG = "lFTAG";
    public static final String NEWSID = "id";
    private SearchView msearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mdrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (RecyclerView) findViewById(R.id.left_drawer);
        mDrawerItemsTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.drawer_items_Titles)));
        mDrawerList.setLayoutManager(new LinearLayoutManager(this));

        drawerItemsAdapter = new DrawerItemsAdapter(this, mDrawerItemsTitles);

        // Set the adapter for the list view
        mDrawerList.setAdapter(drawerItemsAdapter);
        if (savedInstanceState == null) {

            FragmentListingNews fragment = new FragmentListingNews();

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, NEWSLISTINGFRAGMENT_TAG).commit();
        }
        // Set the list's click listener
  /*      mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });
*/

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mdrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mdrawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem searchViewMenuItem = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchViewMenuItem);
        int searchImgId = android.support.v7.appcompat.R.id.search_button; // I used the explicit layout ID of searchview's ImageView
        ImageView v = (ImageView) mSearchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.filter);


        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        msearch = (SearchView) menu.findItem(R.id.action_search).getActionView();
        msearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onListFragmentInteraction(News news) {
        Intent intent = new Intent(this, ActivityNewsDetails.class).putExtra(NEWSID ,news.Nid);
        startActivity(intent);
    }
}
