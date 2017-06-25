package com.codepath.apps.songbirder.timeline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.SongbirderApplication;
import com.codepath.apps.songbirder.api.TwitterClient;
import com.codepath.apps.songbirder.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity
{
    private static final String TAG = TimelineActivity.class.getSimpleName();

    private TweetAdapter adapter;
    ArrayList<Tweet> tweets;

    public static Intent newIntent( Context context )
    {
        return new Intent( context, TimelineActivity.class );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_timeline );

        configureToolbar();
        configureFab();
        configureRecyclerView();
        populateTimeline();
    }

    private void configureFab()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                Snackbar.make( view, "Composing a tweet", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );
    }

    private void configureToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
    }

    private void configureRecyclerView()
    {
        RecyclerView rvTweets = (RecyclerView) findViewById( R.id.rvTweets );
        rvTweets.setLayoutManager( new LinearLayoutManager( this ) );

        tweets = new ArrayList<>();

        adapter = new TweetAdapter( tweets );
        rvTweets.setAdapter( adapter );
    }

    protected void populateTimeline()
    {
        TwitterClient client = SongbirderApplication.getTwitterClient();
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
}
