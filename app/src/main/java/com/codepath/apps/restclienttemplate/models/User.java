package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User
{
    public static final String NAME_KEY = "name";
    public static final String ID_KEY = "id";
    public static final String SCREEN_NAME_KEY = "screenName";
    public static final String PROFILE_IMAGE_URL_KEY = "profileImageUrl";

    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;

    public static User fromJson( JSONObject jsonObject ) throws JSONException
    {
        User user = new User();

        user.name = jsonObject.getString( NAME_KEY );
        user.uid = jsonObject.getLong( ID_KEY );
        user.screenName = jsonObject.getString( SCREEN_NAME_KEY );
        user.profileImageUrl = jsonObject.getString( PROFILE_IMAGE_URL_KEY );

        return user;
    }

    public String getName()
    {
        return name;
    }
}
