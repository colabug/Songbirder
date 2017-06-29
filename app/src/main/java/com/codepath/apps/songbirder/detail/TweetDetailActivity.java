package com.codepath.apps.songbirder.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvUserName) TextView tvUserName;
    @BindView(R.id.tvTweetText) TextView tvTweetText;
    @BindView(R.id.ivVerified) ImageView ivVerified;
    @BindView(R.id.tvRetweets) TextView tvRetweets;
    @BindView(R.id.tvLikes) TextView tvLikes;
    @BindView(R.id.tvTimestamp) TextView tvTimestamp;

    Tweet tweet;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );

        ButterKnife.bind( this );

        Tweet tweet = Parcels.unwrap( getIntent().getParcelableExtra( ARG_DETAIL_TWEET));
        if( tweet == null )
        {
            tweet = new Tweet();
        }

        tvName.setText( tweet.getName() );
        tvUserName.setText( tweet.getDisplayUsername() );
        tvTweetText.setText( tweet.getTweetText() );
        tvTimestamp.setText( tweet.getTimeStamp() );

        Glide.with( this )
             .load( tweet.getProfileImageUrl() )
             .into( ivProfileImage );
    }
}
