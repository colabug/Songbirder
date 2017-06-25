package com.codepath.apps.restclienttemplate.timeline;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.SongbirderApplication;
import com.codepath.apps.restclienttemplate.api.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity
{
    private static final String TAG = TimelineActivity.class.getSimpleName();

    private TwitterClient client;

    private TweetAdapter adapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_timeline );

        rvTweets = (RecyclerView) findViewById( R.id.rvTweets );
        tweets = new ArrayList<>();
        adapter = new TweetAdapter( tweets );

        configureRecyclerView();

        client = SongbirderApplication.getTwitterClient();
        populateTimeline();
    }

    private void configureRecyclerView()
    {
        rvTweets.setLayoutManager( new LinearLayoutManager( this ) );
        rvTweets.setAdapter( adapter );
    }

    protected void populateTimeline()
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
                for( int index = 0; index < response.length(); index++ )
                {
                    try
                    {
                        tweets.add( Tweet.fromJson( response.getJSONObject( index ) ) );
                        adapter.notifyItemInserted( tweets.size() - 1 );
                    }
                    catch( JSONException exception )
                    {
                        exception.printStackTrace();
                    }
                }
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
                Log.d( TAG, "Failed to fetch data: " + responseString );
                throwable.printStackTrace();
            }
        };
    }

    public static Intent newIntent( Context context )
    {
        return new Intent( context, TimelineActivity.class );
    }
}
