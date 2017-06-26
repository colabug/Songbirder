package com.codepath.apps.songbirder.models;

import android.support.annotation.VisibleForTesting;

import com.codepath.apps.songbirder.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet
{
    public static final String BODY_KEY = "text";
    public static final String ID_KEY = "id";
    public static final String CREATED_AT_KEY = "created_at";
    public static final String USER_KEY = "user";

    public long uid;
    public String createdAt;
    public String tweetText;
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
    public Tweet( User user, String tweetText, String timestamp )
    {
        this.user = user;
        this.tweetText = tweetText;
        this.createdAt = timestamp;
    }

    public long getId()
    {
        return uid;
    }

    public String getName()
    {
        return user.getName();
    }

    public String getTweetText()
    {
        return tweetText;
    }

    public String getProfileImageUrl()
    {
        return user.getProfileImageUrl();
    }

    public String getDisplayUsername()
    {
        return "@" + user.getUserName();
    }

    public String getRelativeTimestamp()
    {
        return DateUtil.getRelativeTimestamp( createdAt );
    }
}
