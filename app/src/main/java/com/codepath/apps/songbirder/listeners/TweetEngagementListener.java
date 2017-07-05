package com.codepath.apps.songbirder.listeners;

import com.codepath.apps.songbirder.models.Tweet;

public interface TweetEngagementListener extends BaseEngagementListener
{
    void startReply( String username, long replyId );

    void onLike( long id );
    void onUnlike( Tweet tweet );

    void onRetweet( long id );
    void onUnretweet( Tweet tweet );
}