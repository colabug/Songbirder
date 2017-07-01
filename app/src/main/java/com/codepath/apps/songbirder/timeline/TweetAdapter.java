package com.codepath.apps.songbirder.timeline;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.detail.TweetDetailActivity;
import com.codepath.apps.songbirder.listeners.EngageWithTweetListener;
import com.codepath.apps.songbirder.listeners.EngagementButtonListener;
import com.codepath.apps.songbirder.listeners.ComposeListener;
import com.codepath.apps.songbirder.models.Tweet;
import com.codepath.apps.songbirder.views.TweetEngagementView;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>
                          implements EngagementButtonListener
{
    public static final String ARG_DETAIL_TWEET = "tweet for detail";

    private final List<Tweet> tweets;

    private Context context;

    private EngageWithTweetListener engagementListener;
    private ComposeListener composeListener;

    TweetAdapter( List<Tweet> tweets )
    {
        this.tweets = tweets;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from( context );

        engagementListener = (EngageWithTweetListener) context;
        composeListener = (ComposeListener) context;

        View tweetView = inflater.inflate( R.layout.item_tweet, parent, false);
        return new ViewHolder( tweetView );
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder, final int position )
    {
        final Tweet tweet = tweets.get( position );

        holder.layout.setOnClickListener( getItemClickListener( tweet ) );

        holder.userAndTimeStamp.setText( getFormattedText( tweet ) );
        holder.tvTweetText.setText( tweet.getTweetText() );

        Glide.with( context )
             .load( tweet.getProfileImageUrl() )
             .into( holder.ivProfileImage );

        holder.vTweetEngagement.setTweet( tweet );
        holder.vTweetEngagement.setListener( this );
    }

    private Spanned getFormattedText( Tweet tweet )
    {
        return Html.fromHtml( "<b><font color=#000000>" + tweet.getName() + "</font></b><font color=#8494a3> " +
                              tweet.getDisplayUsername() + " · " +
                              tweet.getRelativeTimestamp() + "</font>" );
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

    @Override
    public void onReplyClick( Tweet tweet )
    {
        composeListener.startReply( tweet.getDisplayUsername(), tweet.getId() );
    }

    @Override
    public void onRetweetClick( Tweet tweet )
    {
        if( tweet.isRetweeted() )
        {
            engagementListener.onUnretweet( tweet );
        }
        else
        {
            engagementListener.onRetweet( tweet.getId() );
        }
    }

    @Override
    public void onLikeClick( Tweet tweet )
    {
        if( tweet.isLiked() )
        {
            engagementListener.onUnlike( tweet );
        }
        else
        {
            engagementListener.onLike( tweet.getId() );
        }
    }

    void clear()
    {
        tweets.clear();
        notifyDataSetChanged();
    }

    void addAll( List<Tweet> list )
    {
        tweets.addAll( list );
        notifyDataSetChanged();
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

        @BindView(R.id.rltweetayout) RelativeLayout layout;

        @BindView(R.id.userAndTimeStamp) TextView userAndTimeStamp;
        @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
        @BindView(R.id.tvTweetText) TextView tvTweetText;

        @BindView(R.id.vTweetEngagement) TweetEngagementView vTweetEngagement;

        ViewHolder( View itemView )
        {
            super( itemView );
            ButterKnife.bind( this, itemView );
        }
    }
}
