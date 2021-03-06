package com.example.news.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.news.helpers.ParseNewsResponse;
import com.example.news.adapters.MyNewsRecyclerViewAdapter;
import com.example.news.Models.NewsResponse;
import com.example.news.helpers.Utility;
import com.example.news.helpers.Services;
import com.example.news.Models.News;
import com.example.news.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FragmentListingNews extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private OnListFragmentInteractionListener mListener;
    private ArrayList<News> mNewsArrayList = new ArrayList<>();
    private MyNewsRecyclerViewAdapter myNewsRecyclerViewAdapter;
    private ProgressBar mProgressBar;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentListingNews() {
    }

    public static FragmentListingNews newInstance() {
        return new FragmentListingNews();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myNewsRecyclerViewAdapter = new MyNewsRecyclerViewAdapter(mNewsArrayList, mListener, getActivity());
        recyclerView.setAdapter(myNewsRecyclerViewAdapter);
//        setListeners();
        return view;
    }
//
//    private void setListeners() {
//        mProgressBar.setOnClickListener(clickListener);
//    }
//
//    private View.OnClickListener clickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//        }
//    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        if (Utility.HaveNetworkConnection(getActivity())) {
            getData();
        } else {
            Toast.makeText(getActivity(), "There is no internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void getData() {
        Services.getInstance(getActivity()).getNews(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //   getNewsDataFromJson(response);
                mNewsArrayList.addAll((ParseNewsResponse.getInstance(getActivity()).getNewsDataFromJson(response)).getNews());
                myNewsRecyclerViewAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void getDataTest() {
        Services.getInstance(getActivity()).getNewsTest(new Response.Listener<List<News>>() {
            @Override
            public void onResponse(List<News> response) {
                mNewsArrayList.addAll(response);
                myNewsRecyclerViewAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_LONG).show();
            }
        });


    }


    private void getNewsDataFromJson(JSONObject response) {

        String newsStr = response.toString();
        Gson gson = new GsonBuilder().create();
        NewsResponse newsResponse = gson.fromJson(newsStr, NewsResponse.class);
        mNewsArrayList.addAll(newsResponse.getNews());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Services.getInstance(getActivity()).getRequestQueue() != null) {
            Services.getInstance(getActivity()).getRequestQueue().cancelAll(Services.Tag.NEWS);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(News item);
    }

}
