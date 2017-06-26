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
    public void onBindViewHolder( ViewHolder holder, int position )
    {
        final Tweet tweet = tweets.get( position );

        holder.tvName.setText( tweet.getName() );
        holder.tvUserName.setText( tweet.getDisplayUsername() );
        holder.tvRelativeTimestamp.setText( tweet.getRelativeTimestamp() );
        holder.tvTweetText.setText( tweet.getTweetText() );
        Glide.with( context )
             .load( tweet.getProfileImageUrl() )
             .into( holder.ivProfileImage );

        holder.btnReply.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                listener.onReply( tweet );
            }
        } );
    }

    @Override
    public int getItemCount()
    {
        return tweets.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.tvUserName) TextView tvUserName;
        @BindView(R.id.tvRelativeTimestamp) TextView tvRelativeTimestamp;
        @BindView(R.id.tvTweetText) TextView tvTweetText;
        @BindView(R.id.ivReply) ImageView btnReply;

        ViewHolder( View itemView )
        {
            super( itemView );
            ButterKnife.bind( this, itemView );
        }
    }
}
