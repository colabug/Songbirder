package com.codepath.apps.songbirder.timeline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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

// TODO: Add error state when can't fetch data
public class TimelineActivity extends AppCompatActivity implements ComposeTweetDialog.ComposeTweetDialogListener,
                                                                   TweetAdapter.ReplyToTweetListener
{
    private static final String TAG = TimelineActivity.class.getSimpleName();

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rvTweets) RecyclerView rvTweets;
    @BindView(R.id.pbProgressBar) ProgressBar pbProgressBar;

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
        showCompositionDialog( null );
    }

    private void showCompositionDialog( Tweet tweet )
    {
        ComposeTweetDialog dialog;

        if( tweet != null )
        {
            dialog = ComposeTweetDialog.newInstance( tweet );
        }
        else
        {
            // TODO: Pass in user's profile URL
            dialog = ComposeTweetDialog.newInstance( null );
        }

        dialog.show( getSupportFragmentManager(), TAG );
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
        showProgressBar();
        client.getHomeTimeline( getTimelineHandler() );
    }

    private void showProgressBar()
    {
        pbProgressBar.setVisibility( View.VISIBLE );
    }

    @NonNull
    private JsonHttpResponseHandler getTimelineHandler()
    {
        return new JsonHttpResponseHandler()
        {
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

                hideProgressBar();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONObject errorResponse )
            {
                if( errorResponse != null )
                {
                    logError( errorResponse.toString(), throwable );
                }
                else
                {
                    logError( "", throwable );
                }
                hideProgressBar();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONArray errorResponse )
            {
                if( errorResponse != null )
                {
                    logError( errorResponse.toString(), throwable );
                }
                else
                {
                    logError( "", throwable );
                }

                hideProgressBar();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable )
            {
                logError( responseString, throwable );
                hideProgressBar();
            }
        };
    }

    private void hideProgressBar()
    {
        pbProgressBar.setVisibility( View.GONE );
    }

    @Override
    public void onReply( Tweet tweet )
    {
        showCompositionDialog( tweet );
    }

    @Override
    public void onRetweet( long id )
    {
        client.retweet( id, getRetweetHandler() );
    }

    @Override
    public void onUnretweet( Tweet tweet )
    {
        client.unretweet( tweet.getId(), getUnretweetHandler( tweet ) );
    }

    private JsonHttpResponseHandler getUnretweetHandler( final Tweet tweet )
    {
        return new JsonHttpResponseHandler(  ) {
            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONObject response )
            {
                super.onSuccess( statusCode, headers, response );
                tweets.remove( tweet );
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONArray response )
            {
                super.onSuccess( statusCode, headers, response );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONObject errorResponse )
            {
                super.onFailure( statusCode, headers, throwable, errorResponse );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONArray errorResponse )
            {
                super.onFailure( statusCode, headers, throwable, errorResponse );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable )
            {
                super.onFailure( statusCode, headers, responseString, throwable );
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, String responseString )
            {
                super.onSuccess( statusCode, headers, responseString );
            }
        };
    }

    @Override
    public void onLike( long id )
    {
        client.like( id, getLikeHandler() );
    }

    @Override
    public void onUnlike( Tweet tweet )
    {
        client.unlike( tweet.getId(), getLikeHandler() );
    }

    private JsonHttpResponseHandler getLikeHandler()
    {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONObject response )
            {
                super.onSuccess( statusCode, headers, response );
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONArray response )
            {
                super.onSuccess( statusCode, headers, response );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONObject errorResponse )
            {
                super.onFailure( statusCode, headers, throwable, errorResponse );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONArray errorResponse )
            {
                super.onFailure( statusCode, headers, throwable, errorResponse );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable )
            {
                super.onFailure( statusCode, headers, responseString, throwable );
            }
        };
    }

    private JsonHttpResponseHandler getRetweetHandler()
    {
        return new JsonHttpResponseHandler(){
            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONObject response )
            {
                Log.d( TAG, "Successfully retweeted" );
                hideProgressBar();

                try
                {
                    // TODO: Associate the new tweet returned with the original tweet in the
                    // timeline instead of showing two tweets.
                    insertTweetAtTop( response );
                }
                catch( JSONException e )
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONObject errorResponse )
            {
                logError( errorResponse.toString(), throwable );
                hideProgressBar();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONArray errorResponse )
            {
                logError( errorResponse.toString(), throwable );
                hideProgressBar();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable )
            {
                logError( responseString, throwable );
                hideProgressBar();
            }
        };
    }

    @Override
    public void onTweetSubmit( String tweetText, long replyId )
    {
        client.postTweet( tweetText, replyId, getStatusPostingHandler() );
        showProgressBar();
        // TODO: When a reply, need to send the username in the text.
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
                    insertTweetAtTop( response );
                }
                catch( JSONException e )
                {
                    e.printStackTrace();
                }
                hideProgressBar();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONObject errorResponse )
            {
                logError( errorResponse.toString(), throwable );
                hideProgressBar();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONArray errorResponse )
            {
                logError( errorResponse.toString(), throwable );
                hideProgressBar();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable )
            {
                logError( responseString, throwable );
                hideProgressBar();
            }
        };
    }

    private void insertTweetAtTop( JSONObject response ) throws JSONException
    {
        tweets.add( 0, Tweet.fromJson( response ) );
        adapter.notifyItemInserted( 0 );
        rvTweets.scrollToPosition( 0 );
    }

    private void logError( String message, Throwable throwable )
    {
        Log.d( TAG, "API failure: " + message );
        throwable.printStackTrace();
    }
}
