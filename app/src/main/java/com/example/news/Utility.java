package com.example.news;

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
}
