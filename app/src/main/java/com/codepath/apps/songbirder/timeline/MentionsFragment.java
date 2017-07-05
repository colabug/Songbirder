package com.codepath.apps.songbirder.timeline;

import com.codepath.apps.songbirder.listeners.ComposeListener;
import com.codepath.apps.songbirder.listeners.TweetEngagementListener;

public class MentionsFragment extends TweetListFragment
                              implements ComposeListener, TweetEngagementListener
{
    @Override
    protected void populateTimeline()
    {
        showProgressBar();
        client.getMentionsTimeline( getTimelineHandler() );
    }

    public static MentionsFragment newInstance()
    {
        return new MentionsFragment();
    }
}
