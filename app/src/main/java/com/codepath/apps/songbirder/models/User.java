package com.codepath.apps.songbirder.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Parcelable
{
    private static final String NAME_KEY = "name";
    private static final String ID_KEY = "id";
    private static final String SCREEN_NAME_KEY = "screen_name";
    private static final String PROFILE_IMAGE_URL_KEY = "profile_image_url";

    private long uid;
    public String name;
    private String screenName;
    private String profileImageUrl;

    private User()
    {
    }

    private User( Parcel in )
    {
        uid = in.readLong();
        name = in.readString();
        screenName = in.readString();
        profileImageUrl = in.readString();
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

    public static final Creator<User> CREATOR = new Creator<User>()
    {
        @Override
        public User createFromParcel( Parcel in )
        {
            return new User( in );
        }

        @Override
        public User[] newArray( int size )
        {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel( Parcel out, int flags )
    {
        out.writeLong(uid);
        out.writeString(name);
        out.writeString(screenName);
        out.writeString(profileImageUrl);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }
}
