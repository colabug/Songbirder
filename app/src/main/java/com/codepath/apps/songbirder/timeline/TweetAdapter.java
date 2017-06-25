package com.codepath.apps.songbirder.timeline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.models.Tweet;

import java.util.List;

class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>
{
    private final List<Tweet> tweets;
    private Context context;

    TweetAdapter( List<Tweet> tweets )
    {
        this.tweets = tweets;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from( context );

        View tweetView = inflater.inflate( R.layout.item_tweet, parent, false);
        return new ViewHolder( tweetView );
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position )
    {
        Tweet tweet = tweets.get( position );

        holder.tvName.setText( tweet.getName() );
        holder.tvUserName.setText( tweet.getDisplayUsername() );
        holder.tvRelativeTimestamp.setText( tweet.getRelativeTimestamp() );
        holder.tvTweet.setText( tweet.getTweetText() );
        Glide.with( context )
             .load( tweet.getProfileImageUrl() )
             .into( holder.ivProfileImage );
    }

    @Override
    public int getItemCount()
    {
        return tweets.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivProfileImage;
        TextView tvName;
        TextView tvUserName;
        TextView tvRelativeTimestamp;
        TextView tvTweet;

        ViewHolder( View itemView )
        {
            super( itemView );

            ivProfileImage = (ImageView) itemView.findViewById( R.id.ivProfileImage );
            tvName = (TextView) itemView.findViewById( R.id.tvName );
            tvUserName = (TextView) itemView.findViewById( R.id.tvUserName );
            tvRelativeTimestamp = (TextView) itemView.findViewById( R.id.tvRelativeTimestamp );
            tvTweet = (TextView) itemView.findViewById( R.id.tvTweetText );
        }
    }
}
