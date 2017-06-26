package com.codepath.apps.songbirder.support;

import android.support.v4.app.FragmentActivity;

import com.codepath.apps.songbirder.ComposeTweetDialog;

public class ActivityWithListener extends FragmentActivity implements ComposeTweetDialog.ComposeTweetDialogListener
{
    public String tweetText;

    @Override
    public void onTweetSubmit( String tweetText, long replyId )
    {
        this.tweetText = tweetText;
    }
}
