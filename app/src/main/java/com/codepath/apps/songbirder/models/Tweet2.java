package com.codepath.apps.songbirder.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.VisibleForTesting;

import com.codepath.apps.songbirder.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

// TODO: Collapse with the normal tweet after figuring out how to show them
//       from the DB dataset instead of the fetched ones from the web.
@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class,
                                  parentColumns = "uid",
                                  childColumns = "user_id"))
public class Tweet2
{
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_BODY = "text";
//    private static final String KEY_USER = "user";
//    private static final String KEY_RETWEET = "retweeted_status";
    private static final String KEY_FAVORITED = "favorited";
    private static final String KEY_FAVORITE_COUNT = "favorite_count";
    private static final String KEY_RETWEET_COUNT = "retweet_count";

    @PrimaryKey
    public long uid;
    public String createdAt;

    public String tweetText;

    @ColumnInfo(name="user_id")
    public long userId;

//    public User user;

    public boolean liked;
    public int likeCount;

//    public RetweetStatus retweetStatus;
    public int retweetCount;

    public Tweet2()
    {
    }

    public static Tweet2 fromJson( JSONObject jsonObject ) throws JSONException
    {
        Tweet2 tweet = new Tweet2();

        tweet.uid = jsonObject.getLong( KEY_ID );
        tweet.createdAt = jsonObject.getString( KEY_CREATED_AT );
        tweet.liked = jsonObject.getBoolean( KEY_FAVORITED );
        tweet.likeCount = jsonObject.getInt( KEY_FAVORITE_COUNT );
        tweet.retweetCount = jsonObject.getInt( KEY_RETWEET_COUNT );
//        tweet.user = User.fromJson( jsonObject.getJSONObject( KEY_USER ) );

//        if( jsonObject.has( KEY_RETWEET ) )
//        {
//            tweet.retweetStatus = RetweetStatus.fromJson( jsonObject.getJSONObject( KEY_RETWEET ) );
//        }

        tweet.tweetText = jsonObject.getString( KEY_BODY );

        return tweet;
    }

    @VisibleForTesting
    public Tweet2(User user, String tweetText, String timestamp )
    {
//        this.user = user;
        this.tweetText = tweetText;
        this.createdAt = timestamp;
    }

    public long getId()
    {
        return uid;
    }

    public String getName()
    {
        return "";
//        return user.getName();
    }

    public String getTweet2Text()
    {
        return tweetText;
    }

    public String getProfileImageUrl()
    {
        return "";// user.getProfileImageUrl();
    }

    public String getDisplayUsername()
    {
        return "";//user.getDisplayUserName();
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
//        return retweetStatus != null && retweetStatus.isRetweeted();
        return false;
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
