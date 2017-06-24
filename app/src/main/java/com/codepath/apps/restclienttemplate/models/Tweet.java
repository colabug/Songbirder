package com.codepath.apps.restclienttemplate.models;

import android.support.annotation.VisibleForTesting;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet
{
    public static final String BODY_KEY = "text";
    public static final String ID_KEY = "id";
    public static final String CREATED_AT_KEY = "createdAt";
    public static final String USER_KEY = "user";

    public String tweetText;
    public long uid;
    public String createdAt;
    public User user;

    public Tweet()
    {
    }

    public static Tweet fromJson( JSONObject jsonObject ) throws JSONException
    {
        Tweet tweet = new Tweet();

        tweet.createdAt = jsonObject.getString( CREATED_AT_KEY );
        tweet.uid = jsonObject.getLong( ID_KEY );
        tweet.user = User.fromJson( jsonObject.getJSONObject( USER_KEY ) );
        tweet.tweetText = jsonObject.getString( BODY_KEY );

        return tweet;
    }

    @VisibleForTesting
    public Tweet( User user, String tweetText )
    {
        this.user = user;
        this.tweetText = tweetText;
    }

    public String getName()
    {
        return user.getName();
    }

    public String getTweetText()
    {
        return tweetText;
    }
}
