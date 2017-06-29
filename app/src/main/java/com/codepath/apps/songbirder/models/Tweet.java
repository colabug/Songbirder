package com.codepath.apps.songbirder.models;

import android.support.annotation.VisibleForTesting;

import com.codepath.apps.songbirder.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

@org.parceler.Parcel
public class Tweet
{
    private static final String KEY_BODY = "text";
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_USER = "user";
    private static final String KEY_RETWEET = "retweeted_status";
    private static final String KEY_FAVORITED = "favorited";

    long uid;
    String createdAt;
    String tweetText;
    boolean favorited;

    User user;
    RetweetStatus retweetStatus;

    public Tweet()
    {
    }

    public static Tweet fromJson( JSONObject jsonObject ) throws JSONException
    {
        Tweet tweet = new Tweet();

        tweet.createdAt = jsonObject.getString( KEY_CREATED_AT );
        tweet.favorited = jsonObject.getBoolean( KEY_FAVORITED );
        tweet.uid = jsonObject.getLong( KEY_ID );
        tweet.user = User.fromJson( jsonObject.getJSONObject( KEY_USER ) );

        if( jsonObject.has( KEY_RETWEET ) )
        {
            tweet.retweetStatus = RetweetStatus.fromJson( jsonObject.getJSONObject( KEY_RETWEET ) );
        }

        tweet.tweetText = jsonObject.getString( KEY_BODY );

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
        return DateUtil.getTimeDifference( createdAt );
    }

    public String getTimeStamp()
    {
        return DateUtil.getTimeStamp( createdAt );
    }

    public boolean isRetweeted()
    {
        return retweetStatus != null && retweetStatus.isRetweeted();
    }
}
