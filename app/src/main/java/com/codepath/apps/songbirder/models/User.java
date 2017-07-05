package com.codepath.apps.songbirder.models;

import android.support.annotation.VisibleForTesting;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User
{
    private static final String KEY_NAME = "name";
    private static final String KEY_ID = "id";
    private static final String KEY_SCREEN_NAME = "screen_name";
    private static final String KEY_PROFILE_IMAGE_URL = "profile_image_url";
    private static final String KEY_BACKGROUND_IMAGE_URL = "profile_background_image_url";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_URL = "url";
    private static final String KEY_FOLLOWER_COUNT = "followers_count";
    private static final String KEY_FRIEND_COUNT = "friends_count";
    private static final String KEY_VERIFIED = "verified";

    long uid;
    String name;
    String screenName;
    private boolean verified;

    String profileImageUrl;
    String backgroundImageUrl;

    String location;
    String description;
    String url;

    int followerCount;
    int friendCount;

    protected User()
    {
    }

    public static User fromJson( JSONObject jsonObject ) throws JSONException
    {
        User user = new User();

        user.name = jsonObject.getString( KEY_NAME );
        user.uid = jsonObject.getLong( KEY_ID );
        user.screenName = jsonObject.getString( KEY_SCREEN_NAME );
        user.verified = jsonObject.getBoolean( KEY_VERIFIED );

        user.profileImageUrl = jsonObject.getString( KEY_PROFILE_IMAGE_URL );
        user.backgroundImageUrl = jsonObject.getString( KEY_BACKGROUND_IMAGE_URL );

        user.location = jsonObject.getString( KEY_LOCATION );
        user.description = jsonObject.getString( KEY_DESCRIPTION );
        user.url = jsonObject.getString( KEY_URL );

        user.followerCount = jsonObject.getInt( KEY_FOLLOWER_COUNT );
        user.friendCount = jsonObject.getInt( KEY_FRIEND_COUNT );

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
