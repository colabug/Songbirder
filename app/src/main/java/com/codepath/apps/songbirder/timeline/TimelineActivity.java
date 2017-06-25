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

import com.codepath.apps.songbirder.ComposeTweetDialog;
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

public class TimelineActivity extends AppCompatActivity implements ComposeTweetDialog.ComposeTweetDialogListener
{
    private static final String TAG = TimelineActivity.class.getSimpleName();

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rvTweets) RecyclerView rvTweets;

    private TweetAdapter adapter;
    ArrayList<Tweet> tweets;
    private TwitterClient client;

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

        client = SongbirderApplication.getTwitterClient();

        setSupportActionBar( toolbar );

        configureRecyclerView();
        populateTimeline();
    }

    @OnClick(R.id.fab) void showComposeDialog()
    {
        // TODO: Get the user's profile image URL, send in here
        ComposeTweetDialog composeTweetDialog = ComposeTweetDialog.newInstance("");
        FragmentManager fm = getSupportFragmentManager();
        composeTweetDialog.show( fm, TAG );
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
        };
    }

    @Override
    public void onTweetEntered( String tweetText )
    {
        client.postNewTweet( tweetText, getStatusPostingHandler() );
    }

    @NonNull
    private JsonHttpResponseHandler getStatusPostingHandler()
    {
        return new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONObject response )
            {
                Log.d( TAG, "Successfully posted tweet!\n" + response );

                try
                {
                    // Add tweet to the top of the list.
                    tweets.add( 0, Tweet.fromJson( response ) );
                    adapter.notifyItemInserted(0);
                    rvTweets.scrollToPosition(0);
                }
                catch( JSONException e )
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONArray response )
            {
                Log.d( TAG, "Successfully posted tweet!\n" + response );
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, String responseString )
            {
                Log.d( TAG, "Successfully posted tweet!" + responseString );
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
        };
    }

    private void logError( String message, Throwable throwable )
    {
        Log.d( TAG, "API failure: " + message );
        throwable.printStackTrace();
    }
}
