package com.codepath.apps.songbirder.timeline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.codepath.apps.songbirder.ComposeTweetBottomSheet;
import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.SongbirderApplication;
import com.codepath.apps.songbirder.api.TwitterClient;
import com.codepath.apps.songbirder.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity
{
    private static final String TAG = TimelineActivity.class.getSimpleName();

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rvTweets) RecyclerView rvTweets;

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

        ButterKnife.bind( this );

        setSupportActionBar( toolbar );

        configureRecyclerView();
        populateTimeline();
    }

    @OnClick(R.id.fab) void showComposeDialog()
    {
        ComposeTweetBottomSheet myDialog = new ComposeTweetBottomSheet();
        FragmentManager fm = getSupportFragmentManager();
        myDialog.show( fm, TAG );
    }


    private void configureRecyclerView()
    {
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
                logError( errorResponse.toString(), throwable );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONArray errorResponse )
            {
                logError( errorResponse.toString(), throwable );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable )
            {
                logError( responseString, throwable );
            }

            private void logError( String message, Throwable throwable )
            {
                Log.d( TAG, "Failed to fetch data: " + message );
                throwable.printStackTrace();
            }
        };
    }
}
