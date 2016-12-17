package com.example.news.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.news.utils.VolleySingleton;
import com.example.news.utils.News;
import com.example.news.R;
import com.example.news.utils.Utility;
import com.example.news.fragments.FragmentListingNews.OnListFragmentInteractionListener;
import com.example.news.views.CircularNetworkImageView;
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

    public MyNewsRecyclerViewAdapter(ArrayList<News> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mcontext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mNews = mValues.get(position);
        mimageLoader = VolleySingleton.getInstance(mcontext).getImageLoader();

        holder.mIcon.setImageUrl(holder.mNews.ImageUrl, mimageLoader);
        holder.mTitle.setText(holder.mNews.NewsTitle);
        holder.mNewsType.setImageResource(Utility.getImageResourceForNewsType(holder.mNews.NewsType));
        holder.mDate.setText(holder.mNews.PostDate);
        holder.mLikes.setText(mcontext.getResources().getString(R.string.likes) + "(" + holder.mNews.Likes + ")");
        holder.mViews.setText(holder.mNews.NumofViews + " " + mcontext.getResources().getString(R.string.views));
        holder.mCardView.setCardBackgroundColor(mcontext.getResources().getColor(R.color.transparentWhite));


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
        public final CircularNetworkImageView mIcon;
        public final TextView mTitle;
        public final ImageView mNewsType;
        public final TextView mDate;
        public final TextView mLikes;
        public final TextView mViews;
        public News mNews;
        public CardView mCardView;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIcon = (CircularNetworkImageView) view.findViewById(R.id.list_item_icon);
            mTitle = (TextView) view.findViewById(R.id.list_item_news_title);
            mNewsType = (ImageView) view.findViewById(R.id.list_item_news_type);
            mDate = (TextView) view.findViewById(R.id.list_item_news_date);
            mLikes = (TextView) view.findViewById(R.id.list_item_news_likes);
            mViews = (TextView) view.findViewById(R.id.list_item_news_views);
            mCardView = (CardView) view.findViewById(R.id.card_view);
        }


    }

}
