package com.codepath.apps.songbirder.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.VisibleForTesting;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
@Entity
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
    private static final String KEY_FOLLOWING_COUNT = "friends_count";
    private static final String KEY_VERIFIED = "verified";

    @PrimaryKey
    public long uid;

    public String name;
    public String screenName;
    public boolean verified;

    public String profileImageUrl;
    public String headerImageUrl;

    public String location;
    public String bio;
    public String url;

    public int followerCount;
    public int followingCount;

    public User()
    {
    }

    // TODO: When null returned, being parsed as "null"
    public static User fromJson( JSONObject jsonObject ) throws JSONException
    {
        User user = new User();

        user.name = jsonObject.getString( KEY_NAME );
        user.uid = jsonObject.getLong( KEY_ID );
        user.screenName = jsonObject.getString( KEY_SCREEN_NAME );
        user.verified = jsonObject.getBoolean( KEY_VERIFIED );

        user.profileImageUrl = jsonObject.getString( KEY_PROFILE_IMAGE_URL );
        user.headerImageUrl = jsonObject.optString( KEY_BACKGROUND_IMAGE_URL );

        user.location = jsonObject.getString( KEY_LOCATION );
        user.bio = jsonObject.getString( KEY_DESCRIPTION );
        user.url = jsonObject.getString( KEY_URL );

        user.followerCount = jsonObject.getInt( KEY_FOLLOWER_COUNT );
        user.followingCount = jsonObject.getInt( KEY_FOLLOWING_COUNT );

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

    public String getDisplayUserName()
    {
        return "@" + screenName;
    }

    public String getBio()
    {
        return bio;
    }

    public String getProfileImageUrl()
    {
        return profileImageUrl;
    }

    public String getHeaderImageUrl()
    {
        return headerImageUrl;
    }

    public String getLocation()
    {
        return location;
    }

    public String getUrl()
    {
        return url;
    }

    public int getFollowerCount()
    {
        return followerCount;
    }

    public int getFollowingCount()
    {
        return followingCount;
    }
}
