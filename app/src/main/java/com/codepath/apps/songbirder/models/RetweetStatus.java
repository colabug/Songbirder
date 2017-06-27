package com.codepath.apps.songbirder.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class RetweetStatus implements Parcelable
{
    private static final String RETWEET_KEY = "retweeted";

    private boolean retweeted = false;

    public RetweetStatus()
    {
    }

    private RetweetStatus( Parcel in )
    {
        retweeted = in.readByte() != 0;
    }

    public static RetweetStatus fromJson( JSONObject jsonObject ) throws JSONException
    {
        RetweetStatus retweet = new RetweetStatus();

        retweet.retweeted = jsonObject.getBoolean( RETWEET_KEY );

        return retweet;
    }

    public static final Creator<RetweetStatus> CREATOR = new Creator<RetweetStatus>()
    {
        @Override
        public RetweetStatus createFromParcel( Parcel in )
        {
            return new RetweetStatus( in );
        }

        @Override
        public RetweetStatus[] newArray( int size )
        {
            return new RetweetStatus[size];
        }
    };

    @Override
    public void writeToParcel( Parcel out, int flags )
    {
        out.writeByte( (byte) ( retweeted ? 1 : 0 ) );
    }

    @Override
    public int describeContents()
    {
        return 0;
    }
}
