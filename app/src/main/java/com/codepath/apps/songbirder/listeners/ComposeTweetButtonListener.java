package com.codepath.apps.songbirder.listeners;

public interface ComposeTweetButtonListener
{
    void onTweetButtonClick( String tweetText );
    void onEditorDone( String tweetText );
}