package com.codepath.apps.songbirder.timeline;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
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

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>
{
    private final List<Tweet> tweets;
    private Context context;
    private ReplyToTweetListener listener;

    interface ReplyToTweetListener
    {
        void onReply( Tweet tweet );
        void onRetweet( long id );
        void onFavorite( long id );
    }

    TweetAdapter( List<Tweet> tweets )
    {
        this.tweets = tweets;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from( context );

        listener = (ReplyToTweetListener) context;

        View tweetView = inflater.inflate( R.layout.item_tweet, parent, false);
        return new ViewHolder( tweetView );
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder, final int position )
    {
        final Tweet tweet = tweets.get( position );

        holder.tvName.setText( tweet.getName() );
        holder.tvUserName.setText( tweet.getDisplayUsername() );
        holder.tvRelativeTimestamp.setText( tweet.getRelativeTimestamp() );
        holder.tvTweetText.setText( tweet.getTweetText() );
        Glide.with( context )
             .load( tweet.getProfileImageUrl() )
             .into( holder.ivProfileImage );

        if( tweet.isRetweeted() )
        {
            holder.btnRetweet.setImageDrawable( holder.retweetActive );
        }
        else
        {
            holder.btnRetweet.setImageDrawable( holder.retweetInactive );
        }

        holder.btnReply.setOnClickListener( getReplyListener( tweet ) );
        holder.btnRetweet.setOnClickListener( getRetweetListener( tweet ) );
        holder.btnFavorite.setOnClickListener( getFavoriteListener( tweet ) );

    }

    @NonNull
    private View.OnClickListener getReplyListener( final Tweet tweet )
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                listener.onReply( tweet );
            }
        };
    }

    @NonNull
    private View.OnClickListener getRetweetListener( final Tweet tweet )
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                listener.onRetweet( tweet.getId() );
            }
        };
    }

    private View.OnClickListener getFavoriteListener( final Tweet tweet )
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                listener.onFavorite( tweet.getId() );
            }
        };
    }

    @Override
    public int getItemCount()
    {
        return tweets.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindDrawable( R.drawable.ic_reply_active ) Drawable replyActive;
        @BindDrawable( R.drawable.ic_reply_inactive ) Drawable replyInactive;
        @BindDrawable( R.drawable.ic_retweet_active ) Drawable retweetActive;
        @BindDrawable( R.drawable.ic_retweet_inactive ) Drawable retweetInactive;
        @BindDrawable( R.drawable.ic_favorite_active ) Drawable favoriteActive;
        @BindDrawable( R.drawable.ic_favorite_inactive ) Drawable favoriteInactive;

        @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.tvUserName) TextView tvUserName;
        @BindView(R.id.tvRelativeTimestamp) TextView tvRelativeTimestamp;
        @BindView(R.id.tvTweetText) TextView tvTweetText;

        @BindView(R.id.ivReply) ImageView btnReply;
        @BindView(R.id.ivRetweet) ImageView btnRetweet;
        @BindView(R.id.ivFavorite) ImageView btnFavorite;

        ViewHolder( View itemView )
        {
            super( itemView );
            ButterKnife.bind( this, itemView );
        }
    }
}
