package com.codepath.apps.songbirder.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;

import com.codepath.apps.songbirder.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet implements Parcelable
{
    private static final String BODY_KEY = "text";
    private static final String ID_KEY = "id";
    private static final String CREATED_AT_KEY = "created_at";
    private static final String USER_KEY = "user";

    private long uid;
    private String createdAt;
    private String tweetText;
    private User user;

    public Tweet()
    {
    }

    private Tweet( Parcel in )
    {
        uid = in.readLong();
        createdAt = in.readString();
        tweetText = in.readString();
        user = in.readParcelable( User.class.getClassLoader() );
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
}
