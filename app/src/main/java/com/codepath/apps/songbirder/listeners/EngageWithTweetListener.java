package com.codepath.apps.songbirder.listeners;

import com.codepath.apps.songbirder.models.Tweet;

public interface EngageWithTweetListener
{
    void onLike( long id );
    void onUnlike( Tweet tweet );

    void onRetweet( long id );
    void onUnretweet( Tweet tweet );
}