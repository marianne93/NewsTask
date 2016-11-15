package com.example.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.news.ListingNewsFragment.OnListFragmentInteractionListener;
// import com.example.news.dummy.DummyContent.News;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link News} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyNewsRecyclerViewAdapter extends RecyclerView.Adapter<MyNewsRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<News> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mcontext;
    private ImageLoader mimageLoader;
    public MyNewsRecyclerViewAdapter(ArrayList<News> items, OnListFragmentInteractionListener listener , Context context) {
        mValues = items;
        mListener = listener;
        mcontext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mNews = mValues.get(position);
        mimageLoader = MySingleton.getInstance(mcontext).getImageLoader();
        holder.mIcon.setImageUrl(holder.mNews.imageIcon, mimageLoader);
        holder.mTitle.setText(holder.mNews.newsTitle);
        holder.mNewsType.setImageUrl(holder.mNews.newsType, mimageLoader);
        holder.mDate.setText(holder.mNews.postDate);
       holder.mLikes.setText(mcontext.getResources().getString(R.string.likes)+"("+holder.mNews.likes+")");
        holder.mViews.setText(holder.mNews.numOfViews +" "+mcontext.getResources().getString(R.string.views));
      //  holder.mIdView.setText(mValues.get(position).id);
     //   holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mNews);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final NetworkImageView mIcon;
        public final TextView mTitle;
        public final NetworkImageView mNewsType;
        public final TextView mDate;
        public final TextView mLikes;
        public final TextView mViews;
        public News mNews;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIcon = (NetworkImageView) view.findViewById(R.id.list_item_icon);
            mTitle = (TextView) view.findViewById(R.id.list_item_news_title);
            mNewsType = (NetworkImageView) view.findViewById(R.id.list_item_news_type);
            mDate = (TextView) view.findViewById(R.id.list_item_news_date);
            mLikes = (TextView) view.findViewById(R.id.list_item_news_likes);
            mViews = (TextView) view.findViewById(R.id.list_item_news_views);
        }

    //    @Override
     //   public String toString() {
    //        return super.toString() + " '" + mContentView.getText() + "'";
    //    }
    }
}
