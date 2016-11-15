package com.example.news;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ListingNewsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    public static ArrayList<News> newsArrayList = new ArrayList<News>();
    MyNewsRecyclerViewAdapter myNewsRecyclerViewAdapter;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListingNewsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
 /*   public static ListingNewsFragment newInstance(int columnCount) {
        ListingNewsFragment fragment = new ListingNewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    } */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
           Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
           if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
           } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            myNewsRecyclerViewAdapter = new MyNewsRecyclerViewAdapter(newsArrayList, mListener , getActivity());
            recyclerView.setAdapter(myNewsRecyclerViewAdapter);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        getData();
    }

    private void getData() {
        final String NEWS_BASE_URL =
                "http://egyptinnovate.com/en/api/v01/safe/GetNews";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(NEWS_BASE_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        getNewsDataFromJson(response);
                        myNewsRecyclerViewAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity() , "called" ,Toast.LENGTH_LONG).show();

                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity() , "calledError" ,Toast.LENGTH_LONG).show();

                    }
                }
        );

        //Adding our request to the queue
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

    }

    private void getNewsDataFromJson(JSONObject response) {
        final String NEWS_LIST = "News";
        final String NEWSTITLE = "NewsTitle";
        final String NID = "Nid";
        final String POSTDATE = "PostDate";
        final String IMAGEURL = "ImageUrl";
        final String NEWSTYPE = "NewsType";
        final String NUMOFVIEWS = "NumofViews";
        final String LIKES = "Likes";
        try {
            JSONArray newsArray = response.getJSONArray(NEWS_LIST);
            News news ;
            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject newsObj = newsArray.getJSONObject(i);
                news = new News();
                news.imageIcon = newsObj.getString(IMAGEURL);
                news.likes = newsObj.getString(LIKES);
                news.newsTitle = newsObj.getString(NEWSTITLE);
                news.newsType = newsObj.getString(NEWSTYPE);
                news.numOfViews = newsObj.getString(NUMOFVIEWS);
                news.postDate = newsObj.getString(POSTDATE);
                news.nId = newsObj.getString(NID);
                newsArrayList.add(news);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


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
        // TODO: Update argument type and name
        void onListFragmentInteraction(News item);
    }

}
