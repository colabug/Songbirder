package com.codepath.apps.restclienttemplate.models;

import android.support.annotation.VisibleForTesting;

import org.json.JSONException;
import org.json.JSONObject;

public class User
{
    public static final String NAME_KEY = "name";
    public static final String ID_KEY = "id";
    public static final String SCREEN_NAME_KEY = "screen_name";
    public static final String PROFILE_IMAGE_URL_KEY = "profile_image_url";

    public long uid;
    public String name;
    public String screenName;
    public String profileImageUrl;

    public User()
    {
    }

    public static User fromJson( JSONObject jsonObject ) throws JSONException
    {
        User user = new User();

        user.name = jsonObject.getString( NAME_KEY );
        user.uid = jsonObject.getLong( ID_KEY );
        user.screenName = jsonObject.getString( SCREEN_NAME_KEY );
        user.profileImageUrl = jsonObject.getString( PROFILE_IMAGE_URL_KEY );

        return user;
    }

    @VisibleForTesting
    public User( String name, String userName )
    {
        this.name = name;
        this.screenName = userName;
    }

    public String getName()
    {
        return name;
    }

    public String getProfileImageUrl()
    {
        return profileImageUrl;
    }

    public String getUserName()
    {
        return screenName;
    }
}
