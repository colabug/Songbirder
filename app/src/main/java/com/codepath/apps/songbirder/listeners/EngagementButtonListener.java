package com.codepath.apps.songbirder.listeners;

import com.codepath.apps.songbirder.models.Tweet;

public interface EngagementButtonListener
{
    void onReplyClick( Tweet tweet );

    void onRetweetClick( Tweet tweet );

    void onLikeClick( Tweet tweet );
}