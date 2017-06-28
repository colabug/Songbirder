package com.codepath.apps.songbirder.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;

import com.codepath.apps.songbirder.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet implements Parcelable
{
    private static final String KEY_BODY = "text";
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_USER = "user";
    private static final String KEY_RETWEET = "retweeted_status";
    private static final String KEY_FAVORITED = "favorited";

    private long uid;
    private String createdAt;
    private String tweetText;
    private boolean favorited;

    private User user;
    private RetweetStatus retweetStatus;

    public Tweet()
    {
    }

    private Tweet( Parcel in )
    {
        uid = in.readLong();
        createdAt = in.readString();
        tweetText = in.readString();
        user = in.readParcelable( User.class.getClassLoader() );
        retweetStatus = in.readParcelable( RetweetStatus.class.getClassLoader() );
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

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>()
    {
        @Override
        public Tweet createFromParcel( Parcel in )
        {
            return new Tweet( in );
        }

        @Override
        public Tweet[] newArray( int size )
        {
            return new Tweet[size];
        }
    };

    @Override
    public void writeToParcel( Parcel out, int flags )
    {
        out.writeLong(uid);
        out.writeString(createdAt);
        out.writeString(tweetText);
        out.writeParcelable( user, flags );
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public boolean isRetweeted()
    {
        return retweetStatus != null && retweetStatus.isRetweeted();
    }
}
