package com.codepath.apps.songbirder;

import com.codepath.apps.songbirder.models.Tweet;

public interface EngageWithTweetListener
{
    void onReply( Tweet tweet );

    void onLike( long id );
    void onUnlike( Tweet tweet );

    void onRetweet( long id );
    void onUnretweet( Tweet tweet );
}