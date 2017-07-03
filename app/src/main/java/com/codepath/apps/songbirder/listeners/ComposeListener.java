package com.codepath.apps.songbirder.listeners;

public interface ComposeListener extends BaseEngagementListener
{
    void tweetComposed( String tweetText, long replyId );
}
