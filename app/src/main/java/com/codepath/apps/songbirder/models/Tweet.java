package com.codepath.apps.songbirder.models;

import android.support.annotation.VisibleForTesting;

import com.codepath.apps.songbirder.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet
{
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_BODY = "text";
    private static final String KEY_USER = "user";
    private static final String KEY_RETWEET = "retweeted_status";
    private static final String KEY_FAVORITED = "favorited";
    private static final String KEY_FAVORITE_COUNT = "favorite_count";
    private static final String KEY_RETWEET_COUNT = "retweet_count";

    long uid;
    String createdAt;

    String tweetText;

    User user;

    boolean liked;
    int likeCount;

    RetweetStatus retweetStatus;
    int retweetCount;

    public Tweet()
    {
    }

    public static Tweet fromJson( JSONObject jsonObject ) throws JSONException
    {
        Tweet tweet = new Tweet();

        tweet.uid = jsonObject.getLong( KEY_ID );
        tweet.createdAt = jsonObject.getString( KEY_CREATED_AT );
        tweet.liked = jsonObject.getBoolean( KEY_FAVORITED );
        tweet.likeCount = jsonObject.getInt( KEY_FAVORITE_COUNT );
        tweet.retweetCount = jsonObject.getInt( KEY_RETWEET_COUNT );
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
        return user.getDisplayUserName();
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

    public boolean isLiked()
    {
        return liked;
    }

    public int getLikeCount()
    {
        return likeCount;
    }

    public int getRetweetCount()
    {
        return retweetCount;
    }

    public boolean hasEngagement()
    {
        return likeCount > 0 && retweetCount >  0;
    }
}
