package com.codepath.apps.restclienttemplate.timeline;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.SongbirderApplication;
import com.codepath.apps.restclienttemplate.api.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity
{
    private static final String TAG = TimelineActivity.class.getSimpleName();

    private TwitterClient client;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_timeline );

        client = SongbirderApplication.getTwitterClient();
        populateTimeline();
    }

    private void populateTimeline()
    {
        client.getHomeTimeline( getTimelineHandler() );
    }

    @NonNull
    private JsonHttpResponseHandler getTimelineHandler()
    {
        return new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONObject response )
            {
                Log.d( TAG, "Successfully fetched data:\n" + response.toString() );
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONArray response )
            {
                Log.d( TAG, "Successfully fetched data:\n" + response.toString() );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONObject errorResponse )
            {
                Log.d( TAG, "Failed to fetch data: " + errorResponse );
                throwable.printStackTrace();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONArray errorResponse )
            {
                Log.d( TAG, "Failed to fetch data: " + errorResponse );
                throwable.printStackTrace();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable )
            {
                Log.d( TAG, "Failed to fetch data: " + responseString);
                throwable.printStackTrace();
            }
        };
    }

    public static Intent newIntent( Context context )
    {
        return new Intent( context, TimelineActivity.class );
    }
}
