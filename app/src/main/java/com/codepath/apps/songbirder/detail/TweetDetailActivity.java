package com.codepath.apps.songbirder.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.models.Tweet;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codepath.apps.songbirder.timeline.TweetAdapter.ARG_DETAIL_TWEET;

public class TweetDetailActivity extends AppCompatActivity
{
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvUserName) TextView tvUserName;
    @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
    @BindView(R.id.ivVerified) ImageView ivVerified;
    @BindView(R.id.tvTweetText) TextView tvTweetText;
    @BindView(R.id.tvTimestamp) TextView tvTimestamp;
    @BindView(R.id.tvRetweets) TextView tvRetweets;
    @BindView(R.id.tvLikes) TextView tvLikes;
    @BindView(R.id.vEngagementDivider) View vEngagementDivider;

    Tweet tweet;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );

        ButterKnife.bind( this );

        // Get tweet from the intent
        tweet = Parcels.unwrap( getIntent().getParcelableExtra( ARG_DETAIL_TWEET));
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
    }

    @SuppressLint("DefaultLocale")
    private String getFormattedString( int value, int resId )
    {
        return String.format( getResources().getQuantityString( resId, value ), value );
    }
}
