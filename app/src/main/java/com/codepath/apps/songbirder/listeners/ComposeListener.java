package com.codepath.apps.songbirder.listeners;

public interface ComposeListener
{
    void startReply( String username, long replyId );
    void tweetComposed( String tweetText, long replyId );
}
