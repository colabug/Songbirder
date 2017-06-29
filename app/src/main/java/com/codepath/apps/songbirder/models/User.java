package com.codepath.apps.songbirder.models;

import android.support.annotation.VisibleForTesting;

import org.json.JSONException;
import org.json.JSONObject;

@org.parceler.Parcel
public class User
{
    private static final String NAME_KEY = "name";
    private static final String ID_KEY = "id";
    private static final String SCREEN_NAME_KEY = "screen_name";
    private static final String PROFILE_IMAGE_URL_KEY = "profile_image_url";

    long uid;
    String name;
    String screenName;
    String profileImageUrl;

    private User()
    {
    }

    static User fromJson( JSONObject jsonObject ) throws JSONException
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

    String getProfileImageUrl()
    {
        return profileImageUrl;
    }

    String getUserName()
    {
        return screenName;
    }
}
