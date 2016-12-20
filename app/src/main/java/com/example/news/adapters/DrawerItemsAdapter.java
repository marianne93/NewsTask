package com.example.news.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news.R;
import com.example.news.helpers.Utility;

import java.util.ArrayList;

/**
 * Created by Marianne on 12-Nov-16.
 */

public class DrawerItemsAdapter  extends RecyclerView.Adapter<DrawerItemsAdapter.ViewHolder> {
// OnListFragmentInteractionListener listener, Context context
private Context mContext;
    private ArrayList<String> mDrawerItems = new ArrayList<>() ;

    public DrawerItemsAdapter(Context context, ArrayList<String> drawerItems) {
        mContext = context;
        mDrawerItems = drawerItems;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView iconView;
        public final TextView titleView;



        public ViewHolder(View view) {
            super(view);

            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            titleView = (TextView) view.findViewById(R.id.list_item_title_textview);
        }
    }

    @Override
    public void onBindViewHolder(DrawerItemsAdapter.ViewHolder holder, int position) {

        holder.iconView.setImageResource(Utility.getImageResourceForDrawerItems(position));
        holder.titleView.setText(mDrawerItems.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return mDrawerItems.size();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_list_item, parent, false);

        return new ViewHolder(view);
    }


}