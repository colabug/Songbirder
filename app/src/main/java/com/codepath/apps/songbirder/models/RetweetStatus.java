package com.codepath.apps.songbirder.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
class RetweetStatus
{
    private static final String RETWEET_KEY = "retweeted";

    boolean retweeted = false;

    public RetweetStatus()
    {
    }

    static RetweetStatus fromJson( JSONObject jsonObject ) throws JSONException
    {
        RetweetStatus retweet = new RetweetStatus();

        retweet.retweeted = jsonObject.getBoolean( RETWEET_KEY );

        return retweet;
    }

    boolean isRetweeted()
    {
        return retweeted;
    }
}
