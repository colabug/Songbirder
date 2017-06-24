package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet
{
    public static final String BODY_KEY = "text";
    public static final String ID_KEY = "id";
    public static final String CREATED_AT_KEY = "createdAt";
    public static final String USER_KEY = "user";

    public String body;
    public long uid;
    public String createdAt;
    public User user;

    public static Tweet fromJson( JSONObject jsonObject ) throws JSONException
    {
        Tweet tweet = new Tweet();

        tweet.body = jsonObject.getString( BODY_KEY );
        tweet.uid = jsonObject.getLong( ID_KEY );
        tweet.createdAt = jsonObject.getString( CREATED_AT_KEY );
        tweet.user = User.fromJson( jsonObject.getJSONObject( USER_KEY ) );

        return tweet;
    }

}
