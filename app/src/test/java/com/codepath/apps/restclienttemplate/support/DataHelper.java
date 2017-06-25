package com.codepath.apps.restclienttemplate.support;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;

import java.util.ArrayList;


public class DataHelper
{
    private static final String TWEET_TEXT = "Hello, this is a tweet.";
    private static final String TWEET_NAME = "Corey Latislaw";
    private static final String TWEET_USERNAME = "Corey_Latislaw";

    public static ArrayList<Tweet> populateList()
    {
        ArrayList<Tweet> list = new ArrayList<>();

        User user = new User( TWEET_NAME, TWEET_USERNAME );
        Tweet tweet = new Tweet( user, TWEET_TEXT );
        list.add( tweet );
        list.add( tweet );
        list.add( tweet );
        list.add( tweet );

        return list;
    }
}
