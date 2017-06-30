package com.codepath.apps.songbirder.timeline;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.detail.TweetDetailActivity;
import com.codepath.apps.songbirder.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>
{
    public static final String ARG_DETAIL_TWEET = "tweet for detail";
    private final List<Tweet> tweets;
    private Context context;
    private ReplyToTweetListener listener;

    interface ReplyToTweetListener
    {
        void onReply( Tweet tweet );

        void onLike( long id );
        void onUnlike( Tweet tweet );

        void onRetweet( long id );
        void onUnretweet( Tweet tweet );
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

        holder.layout.setOnClickListener( getItemClickListener( tweet ) );

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

        if( tweet.isLiked() )
        {
            holder.btnLike.setImageDrawable( holder.likeActive );
        }
        else
        {
            holder.btnLike.setImageDrawable( holder.likeInactive );
        }

        holder.btnReply.setOnClickListener( getReplyListener( tweet ) );
        holder.btnRetweet.setOnClickListener( getRetweetListener( tweet ) );
        holder.btnLike.setOnClickListener( getLikeListener( tweet ) );
    }

    private View.OnClickListener getItemClickListener( final Tweet tweet )
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                Intent intent = new Intent( context, TweetDetailActivity.class );
                intent.putExtra( ARG_DETAIL_TWEET, Parcels.wrap( tweet ) );
                context.startActivity( intent );
            }
        };
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
                if( tweet.isRetweeted() )
                {
                    listener.onUnretweet( tweet );
                }
                else
                {
                    listener.onRetweet( tweet.getId() );
                }
            }
        };
    }

    private View.OnClickListener getLikeListener( final Tweet tweet )
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
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
        @BindDrawable( R.drawable.ic_like_active) Drawable likeActive;
        @BindDrawable( R.drawable.ic_like_inactive) Drawable likeInactive;

        @BindView(R.id.rltweetayout) RelativeLayout layout;
        @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.tvUserName) TextView tvUserName;
        @BindView(R.id.tvRelativeTimestamp) TextView tvRelativeTimestamp;
        @BindView(R.id.tvTweetText) TextView tvTweetText;

        @BindView(R.id.ivReply) ImageView btnReply;
        @BindView(R.id.ivRetweet) ImageView btnRetweet;
        @BindView(R.id.ivLike) ImageView btnLike;

        ViewHolder( View itemView )
        {
            super( itemView );
            ButterKnife.bind( this, itemView );
        }
    }
}
