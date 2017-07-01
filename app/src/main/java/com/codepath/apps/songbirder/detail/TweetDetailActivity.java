package com.codepath.apps.songbirder.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.SongbirderApplication;
import com.codepath.apps.songbirder.api.TwitterClient;
import com.codepath.apps.songbirder.compose.ComposeTweetDialog;
import com.codepath.apps.songbirder.listeners.EngageWithTweetListener;
import com.codepath.apps.songbirder.listeners.EngagementButtonListener;
import com.codepath.apps.songbirder.models.Tweet;
import com.codepath.apps.songbirder.views.TweetEngagementView;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.songbirder.timeline.TweetAdapter.ARG_DETAIL_TWEET;

// TODO: Add media
public class TweetDetailActivity extends AppCompatActivity
                                 implements EngagementButtonListener,
                                            ComposeTweetDialog.ComposeTweetDialogListener,
                                            EngageWithTweetListener
{
    private static final String TAG = TweetDetailActivity.class.getSimpleName();

    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvUserName) TextView tvUserName;
    @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
    @BindView(R.id.ivVerified) ImageView ivVerified;
    @BindView(R.id.tvTweetText) TextView tvTweetText;
    @BindView(R.id.tvTimestamp) TextView tvTimestamp;
    @BindView(R.id.tvRetweets) TextView tvRetweets;
    @BindView(R.id.tvLikes) TextView tvLikes;
    @BindView(R.id.vEngagementDivider) View vEngagementDivider;
    @BindView(R.id.vTweetEngagement) TweetEngagementView vTweetEngagement;

    Tweet tweet;

    private EngageWithTweetListener listener;

    private TwitterClient client;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );

        ButterKnife.bind( this );

        listener = this;

        client = SongbirderApplication.getTwitterClient();

        // Get tweet from the intent
        tweet = Parcels.unwrap( getIntent().getParcelableExtra( ARG_DETAIL_TWEET ) );
        if( tweet == null )
        {
            tweet = new Tweet();
        }

        // Populate user data
        tvName.setText( tweet.getName() );
        tvUserName.setText( tweet.getDisplayUsername() );
        Glide.with( this )
             .load( tweet.getProfileImageUrl() )
             .into( ivProfileImage );

        // Populate tweet data
        tvTweetText.setText( tweet.getTweetText() );
        tvTimestamp.setText( tweet.getTimeStamp() );

        // Populate retweet data
        if( tweet.getRetweetCount() > 0 )
        {
            tvRetweets.setVisibility( View.VISIBLE );
            tvRetweets.setText( getFormattedString( tweet.getRetweetCount(),
                                                    R.plurals.detail_retweet_plural ) );
        }
        else
        {
            tvRetweets.setVisibility( View.GONE );
        }

        // Populate likes data
        if( tweet.getLikeCount() > 0 )
        {
            tvLikes.setVisibility( View.VISIBLE );
            tvLikes.setText( getFormattedString( tweet.getLikeCount(),
                                                 R.plurals.detail_like_plural ) );
        }
        else
        {
            tvLikes.setVisibility( View.GONE );
        }

        // Hide or show divider when engagement present
        if( tweet.hasEngagement() )
        {
            vEngagementDivider.setVisibility( View.VISIBLE );
        }
        else
        {
            vEngagementDivider.setVisibility( View.GONE );
        }

        vTweetEngagement.setListener( this );
        vTweetEngagement.setTweet( tweet );
    }

    @SuppressLint("DefaultLocale")
    private String getFormattedString( int value, int resId )
    {
        return String.format( getResources().getQuantityString( resId, value ), value );
    }

    @Override
    public void onReplyClick( Tweet tweet )
    {
        listener.onReply( tweet );
    }

    @Override
    public void onRetweetClick( Tweet tweet )
    {
        if( tweet.isRetweeted() )
        {
            listener.onUnretweet( tweet );
        }
        else
        {
            listener.onRetweet( tweet.getId() );
        }
    }

    @Override
    public void onLikeClick( Tweet tweet )
    {
        if( tweet.isLiked() )
        {
            listener.onUnlike( tweet );
        }
        else
        {
            listener.onLike( tweet.getId() );
        }
    }

    @Override
    public void onReply( Tweet tweet )
    {
        Toast.makeText(this, "Replying", Toast.LENGTH_LONG).show();
        ComposeTweetDialog dialog = ComposeTweetDialog.newInstance( tweet );
        dialog.show( getSupportFragmentManager(), TAG );
    }

    @Override
    public void onTweetSubmit( String tweetText, long replyId )
    {
        client.postTweet( tweetText, replyId, getStatusPostingHandler() );
    }

    private JsonHttpResponseHandler getStatusPostingHandler()
    {
        return new JsonHttpResponseHandler(){
            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONObject response )
            {
                super.onSuccess( statusCode, headers, response );
                // TODO: Handle success here
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONArray response )
            {
                super.onSuccess( statusCode, headers, response );
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, String responseString )
            {
                super.onSuccess( statusCode, headers, responseString );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONObject errorResponse )
            {
                super.onFailure( statusCode, headers, throwable, errorResponse );
                // TODO: Handle already posted status here
                Toast.makeText( TweetDetailActivity.this, "That tweet already exists.", Toast.LENGTH_LONG ).show();
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

    @Override
    public void onRetweet( long id )
    {
        Toast.makeText(this, "Retweeting", Toast.LENGTH_LONG).show();
        client.retweet( id, getRetweetHandler() );
    }

    private JsonHttpResponseHandler getRetweetHandler()
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
            public void onSuccess( int statusCode, Header[] headers, String responseString )
            {
                super.onSuccess( statusCode, headers, responseString );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONObject errorResponse )
            {
                super.onFailure( statusCode, headers, throwable, errorResponse );
                // TODO: Handle already retweeted error here
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

    @Override
    public void onUnretweet( Tweet tweet )
    {
        Toast.makeText(this, "Unretweeting", Toast.LENGTH_LONG).show();
        client.unretweet( tweet.getId(), getUnretweetHandler() );
    }

    private JsonHttpResponseHandler getUnretweetHandler()
    {
        return new JsonHttpResponseHandler(  )
        {
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
            public void onSuccess( int statusCode, Header[] headers, String responseString )
            {
                super.onSuccess( statusCode, headers, responseString );
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

    @Override
    public void onLike( long id )
    {
        Toast.makeText(this, "Liking", Toast.LENGTH_LONG).show();
        client.like( id, getLikeHandler() );
    }

    @Override
    public void onUnlike( Tweet tweet )
    {
        Toast.makeText(this, "Unliking", Toast.LENGTH_LONG).show();
        client.unlike( tweet.getId(), getLikeHandler() );
    }

    private JsonHttpResponseHandler getLikeHandler()
    {
        return new JsonHttpResponseHandler(  ){
            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONObject response )
            {
                super.onSuccess( statusCode, headers, response );
                // TODO: Handle response here
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONArray response )
            {
                super.onSuccess( statusCode, headers, response );
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, String responseString )
            {
                super.onSuccess( statusCode, headers, responseString );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONObject errorResponse )
            {
                super.onFailure( statusCode, headers, throwable, errorResponse );
                // TODO: Handle already liked failure here
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
}
