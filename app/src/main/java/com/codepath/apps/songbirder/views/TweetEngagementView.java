package com.codepath.apps.songbirder.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.listeners.EngagementButtonListener;
import com.codepath.apps.songbirder.models.Tweet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TweetEngagementView extends LinearLayout
{
    @BindView(R.id.ivReply) ImageView btnReply;
    @BindView(R.id.ivRetweet) TextView btnRetweet;
    @BindView(R.id.ivLike) TextView btnLike;
    @BindView(R.id.ivDirectMessage) ImageView ivDirectMessage;

    private Tweet tweet;

    private EngagementButtonListener listener;

    public TweetEngagementView( Context context )
    {
        super( context );
        init();
    }

    public TweetEngagementView( Context context, AttributeSet attrs )
    {
        super( context, attrs );
        init();
    }

    public TweetEngagementView( Context context, AttributeSet attrs, int defStyleAttr )
    {
        super( context, attrs, defStyleAttr );
        init();
    }

    private void init()
    {
        inflate( getContext(), R.layout.view_tweet_engagement, this );

        ButterKnife.bind( this );
    }

    public void setListener( EngagementButtonListener listener )
    {
        this.listener = listener;
    }

    public void setTweet( Tweet tweet )
    {
        this.tweet = tweet;
        setUpView();
    }

    public void hideMessages()
    {
        ivDirectMessage.setVisibility( View.GONE );
    }

    private void setUpView()
    {
        setUpRetweetView();
        setUpLikeView();
    }

    private void setUpRetweetView()
    {
        populateRetweetCount();
        populateRetweetImage();
    }

    private void populateRetweetCount()
    {
        if( tweet.getRetweetCount() > 0 )
        {
            btnRetweet.setText( String.valueOf( tweet.getRetweetCount() ) );
        }
        else
        {
            btnRetweet.setText( "" );
        }
    }

    private void populateRetweetImage()
    {
        if( tweet.isRetweeted() )
        {
            btnRetweet.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_retweet_active, 0, 0, 0 );
        }
        else
        {
            btnRetweet.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_retweet_inactive, 0, 0, 0 );
        }
    }

    private void setUpLikeView()
    {
        populateLikeCount();
        populateLikeImage();
    }

    private void populateLikeCount()
    {
        if( tweet.getLikeCount() > 0 )

        {
            btnLike.setText( String.valueOf( tweet.getLikeCount() ) );
        }
        else
        {
            btnLike.setText( "" );
        }
    }

    private void populateLikeImage()
    {
        if( tweet.isLiked() )
        {
            btnLike.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_like_active, 0, 0, 0 );
        }
        else
        {
            btnLike.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_like_inactive, 0, 0, 0 );
        }
    }

    @OnClick(R.id.ivReply)
    void replyToTweet()
    {
        listener.onReplyClick( tweet );
    }

    @OnClick(R.id.ivRetweet)
    void retweetTweet()
    {
        listener.onRetweetClick( tweet );
    }

    @OnClick(R.id.ivLike)
    void likeTweet()
    {
        listener.onLikeClick( tweet );
    }
}
