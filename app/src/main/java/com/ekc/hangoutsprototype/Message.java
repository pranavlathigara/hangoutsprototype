package com.ekc.hangoutsprototype;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Dummy data for Messages
 */
public class Message {

    public static final String[] DATES = { "Today", "Yesterday", "This month"};

    String mDate;
    String mTimestamp;
    String mFrom;
    String mText;
    int mPicResourceId;

    public Message(String date, String timestamp, String from, String text, Integer picResourceId) {
        mDate = date;
        mTimestamp = timestamp;
        mFrom = from;
        mText = text;
        if (picResourceId == null) {
            mPicResourceId = R.drawable.person_image_empty;
        } else {
            mPicResourceId = picResourceId;
        }
    }

    public static final Message[] MESSAGES = {
            new Message(DATES[0], "4 min", "Andrew Clark", null, R.drawable.emilio_estevez),
            new Message(DATES[1], "9:32 PM", "Molly Ringwald",null, R.drawable.claire_standish),
            new Message(DATES[2], "Feb 11", "Brian Johnson", null, R.drawable.anthony_michael_hall),
            new Message(DATES[0], "42 min", "John Bender", null, R.drawable.judd_nelson),
            new Message(DATES[0], "11:19 AM", "Allison Reynolds", null, R.drawable.ally_sheedy),
            new Message(DATES[2], "Jan 31", "Richard Vernon", null, R.drawable.paul_gleason),
            new Message(DATES[2], "12/12/14", "Carl", null, R.drawable.john_kapelos),
            new Message(DATES[2], "11/16/14", "Anonymous", null, null)
    };
}

