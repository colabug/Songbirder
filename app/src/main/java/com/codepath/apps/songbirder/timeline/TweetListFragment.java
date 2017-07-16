package com.codepath.apps.songbirder.timeline;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.apps.songbirder.BaseFragment;
import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.SongbirderApplication;
import com.codepath.apps.songbirder.api.TwitterClient;
import com.codepath.apps.songbirder.compose.ComposeTweetDialog;
import com.codepath.apps.songbirder.db.AppDatabase;
import com.codepath.apps.songbirder.listeners.ComposeListener;
import com.codepath.apps.songbirder.listeners.TweetEngagementListener;
import com.codepath.apps.songbirder.models.Tweet;
import com.codepath.apps.songbirder.models.Tweet2;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

// TODO: Handle unliking
// TODO: Handle liking better
public abstract class TweetListFragment extends BaseFragment
                                        implements ComposeListener, TweetEngagementListener
{
    public static final String TAG = TimelineFragment.class.getSimpleName();

    @BindView(R.id.rvTweets) RecyclerView rvTweets;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.pbProgressBar) ProgressBar pbProgressBar;
    @BindView(R.id.llErrorView) LinearLayout llErrorView;

    private AppDatabase database;

    private TweetViewModel viewModel;

    private TweetAdapter adapter;
    ArrayList<Tweet> tweets;
    protected TwitterClient client;

    abstract protected void populateTimeline();

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,
                              @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState )
    {
        View layout = inflater.inflate( R.layout.fragment_timeline, container, false );

        ButterKnife.bind( this, layout );

        client = SongbirderApplication.getTwitterClient();

        configureRecyclerView();

        swipeContainer.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                fetchTimelineAsync( 0 );
            }
        } );

        swipeContainer.setColorSchemeResources( android.R.color.holo_blue_bright,
                                                android.R.color.holo_green_light,
                                                android.R.color.holo_orange_light,
                                                android.R.color.holo_red_light );

        populateTimeline();

        viewModel = ViewModelProviders.of(this).get(TweetViewModel.class);
        subscribeToTweets();

        return layout;
    }

    private void subscribeToTweets() {
        viewModel.tweets.observe(this, new Observer<List<Tweet2>>() {
            @Override
            public void onChanged(@NonNull final List<Tweet2> tweets) {
                Log.d(TAG, "Got DB tweets");
            }
        });
    }

    public void fetchTimelineAsync( int page )
    {
        client.getHomeTimeline( new JsonHttpResponseHandler()
        {

            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONObject response )
            {
                stopRefreshing();
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONArray response )
            {
                refreshTweets( response );
                stopRefreshing();
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, String responseString )
            {
                stopRefreshing();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONObject errorResponse )
            {
                stopRefreshing();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONArray errorResponse )
            {
                stopRefreshing();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable )
            {
                stopRefreshing();
            }
        } );
    }

    private void refreshTweets( JSONArray json )
    {
        adapter.clear();
        parseTweets( json );
        adapter.addAll( tweets );
    }

    private void stopRefreshing()
    {
        swipeContainer.setRefreshing( false );
    }

    private void configureRecyclerView()
    {
        rvTweets.setLayoutManager( new LinearLayoutManager( getContext() ) );

        tweets = new ArrayList<>();

        adapter = new TweetAdapter( tweets, this );
        rvTweets.setAdapter( adapter );
    }


    protected void showProgressBar()
    {
        pbProgressBar.setVisibility( View.VISIBLE );
    }

    @NonNull
    protected JsonHttpResponseHandler getTimelineHandler()
    {
        return new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONArray response )
            {
                Log.d( TAG, "Successfully fetched data:\n" + response.toString() );

                parseTweets( response );
                hideProgressBar();
                showErrorOrList();
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
                showErrorOrList();
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
                showErrorOrList();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable )
            {
                logError( responseString, throwable );

                hideProgressBar();
                showErrorOrList();
            }
        };
    }

    private void parseTweets( JSONArray response )
    {
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

    private void showErrorOrList()
    {
        if( tweets.size() <= 0 )
        {
            rvTweets.setVisibility( View.GONE );
            llErrorView.setVisibility( View.VISIBLE );
        }
        else
        {
            rvTweets.setVisibility( View.VISIBLE );
            llErrorView.setVisibility( View.GONE );
        }
    }

    private void hideProgressBar()
    {
        pbProgressBar.setVisibility( View.GONE );
    }

    @Override
    public void tweetComposed( String tweetText, long replyId )
    {
        client.postTweet( tweetText, replyId, getStatusPostingHandler() );
        showProgressBar();
    }

    @Override
    public void startReply( String username, long replyId )
    {
        ComposeTweetDialog dialog = ComposeTweetDialog.newInstance( username, replyId );
        dialog.setListener( this );
        dialog.setTargetFragment( this, COMPOSE_REQUEST_CODE );
        dialog.show( getActivity().getSupportFragmentManager(), TAG );
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
        return new JsonHttpResponseHandler()
        {
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
                showErrorOrList();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONObject errorResponse )
            {
                showErrorOrList();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONArray errorResponse )
            {
                showErrorOrList();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable )
            {
                showErrorOrList();
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, String responseString )
            {
                showErrorOrList();
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
        return new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONObject response )
            {
                showErrorOrList();
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONArray response )
            {
                showErrorOrList();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONObject errorResponse )
            {
                showErrorOrList();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONArray errorResponse )
            {
                showErrorOrList();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable )
            {
                showErrorOrList();
            }
        };
    }

    private JsonHttpResponseHandler getRetweetHandler()
    {
        return new JsonHttpResponseHandler()
        {
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
                showErrorOrList();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONArray errorResponse )
            {
                logError( errorResponse.toString(), throwable );

                hideProgressBar();
                showErrorOrList();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable )
            {
                logError( responseString, throwable );

                hideProgressBar();
                showErrorOrList();
            }
        };
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
                showErrorOrList();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONObject errorResponse )
            {
                logError( errorResponse.toString(), throwable );
                Toast.makeText( getActivity(), "That tweet already exists.", Toast.LENGTH_LONG )
                     .show();
                hideProgressBar();
                showErrorOrList();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONArray errorResponse )
            {
                logError( errorResponse.toString(), throwable );

                hideProgressBar();
                showErrorOrList();
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable )
            {
                logError( responseString, throwable );

                hideProgressBar();
                showErrorOrList();
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
