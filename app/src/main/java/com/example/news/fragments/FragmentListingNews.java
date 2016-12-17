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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.news.adapters.MyNewsRecyclerViewAdapter;
import com.example.news.utils.VolleySingleton;
import com.example.news.utils.News;
import com.example.news.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FragmentListingNews extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "RequestTag";
    private OnListFragmentInteractionListener mListener;
    public static ArrayList<News> newsArrayList = new ArrayList<>();
    MyNewsRecyclerViewAdapter myNewsRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentListingNews() {
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myNewsRecyclerViewAdapter = new MyNewsRecyclerViewAdapter(newsArrayList, mListener, getActivity());
        recyclerView.setAdapter(myNewsRecyclerViewAdapter);
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

    private void getNewsDataFromJson(JSONObject response) {
        final String NEWS_LIST = "News";

        Gson gson = new Gson();

        try {
            JSONArray newsArray = response.getJSONArray(NEWS_LIST);
            String newsStr = newsArray.toString();

            List<News> newsResponse = Arrays.asList(gson.fromJson(newsStr, News[].class));
            newsArrayList.addAll(newsResponse);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Expected a string but was BEGIN_ARRAY
      /*  String newsStr = response.toString();

        NewsResponse newsResponse =gson.fromJson(newsStr, NewsResponse.class);
        newsArrayList.addAll(newsResponse.news); */


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
        if (VolleySingleton.getInstance(getActivity()).getRequestQueue() != null) {
            VolleySingleton.getInstance(getActivity()).getRequestQueue().cancelAll(TAG);
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
