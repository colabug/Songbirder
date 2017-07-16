package com.codepath.apps.songbirder;

import android.app.Application;
import android.content.Context;

import com.codepath.apps.songbirder.api.TwitterClient;

public class SongbirderApplication extends Application
{
    // TODO: Fix this
    private static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        SongbirderApplication.context = this;
    }

    public static TwitterClient getTwitterClient()
    {
        return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, SongbirderApplication.context);
    }
}