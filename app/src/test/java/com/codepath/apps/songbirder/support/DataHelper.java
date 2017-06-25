package com.codepath.apps.songbirder.support;

import com.codepath.apps.songbirder.models.Tweet;
import com.codepath.apps.songbirder.models.User;

import java.util.ArrayList;


public class DataHelper
{
    private static final String TWEET_TEXT = "Hello, this is a tweet.";
    private static final String TWEET_NAME = "Corey Latislaw";
    private static final String TWEET_USERNAME = "Corey_Latislaw";
    private static final String TWEET_TIMESTAMP = "Sun Jun 25 02:20:26 +0000 2017";

    public static ArrayList<Tweet> populateList()
    {
        ArrayList<Tweet> list = new ArrayList<>();

        User user = new User( TWEET_NAME, TWEET_USERNAME );
        Tweet tweet = new Tweet( user, TWEET_TEXT, TWEET_TIMESTAMP );
        list.add( tweet );
        list.add( tweet );
        list.add( tweet );
        list.add( tweet );

        return list;
    }
}
