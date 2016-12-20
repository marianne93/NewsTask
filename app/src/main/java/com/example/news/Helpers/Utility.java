package com.example.news.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.news.R;

/**
 * Created by Marianne on 12-Nov-16.
 */

public class Utility {
    public static int getImageResourceForDrawerItems(int positionId) {
        final int NEWS = 0;
        final int MAP = 1;
        final int CALENDER = 2;
        final int LEADERSHIP = 3;
        final int ARABIC = 4;


        switch (positionId) {
            case NEWS: {
                return R.drawable.news_icon;

            }
            case MAP: {
                return R.drawable.map_icon;

            }
            case CALENDER: {
                return R.drawable.events_icon;

            }
            case LEADERSHIP: {
                return R.drawable.leadership_icon;

            }
            case ARABIC: {
                return R.drawable.language;

            }


        }


        return -1;
    }

    public static int getImageResourceForNewsType(String newsTypeId) {
        switch (newsTypeId) {
            case "84": {
                return R.drawable.article_label;

            }
            case "85": {
                return R.drawable.video_label;

            }


        }
        return -1;
    }
    public static boolean HaveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.getType() == ConnectivityManager.TYPE_WIFI)
                    haveConnectedWifi = true;
            if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
