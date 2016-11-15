package com.example.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marianne on 12-Nov-16.
 */

public class DrawerItemsAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mDrawerItems = new ArrayList<>() ;

    public DrawerItemsAdapter(Context context, ArrayList<String> drawerItems) {
        mContext = context;
        mDrawerItems = drawerItems;

    }

    public static class ViewHolder {
        public final ImageView iconView;
        public final TextView titleView;


        public ViewHolder(View view) {
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            titleView = (TextView) view.findViewById(R.id.list_item_title_textview);
        }
    }

    @Override
    public int getCount() {
        return mDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.drawer_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.iconView.setImageResource(Utility.getImageResourceForDrawerItems(position));
        viewHolder.titleView.setText(mDrawerItems.get(position).toString());
        return convertView;

    }


}