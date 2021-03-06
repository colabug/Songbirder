package com.codepath.apps.songbirder.timeline;

import com.codepath.apps.songbirder.listeners.ComposeListener;
import com.codepath.apps.songbirder.listeners.TweetEngagementListener;

public class TimelineFragment extends TweetListFragment
                              implements ComposeListener, TweetEngagementListener
{
    @Override
    protected void populateTimeline()
    {
        showProgressBar();
        client.getHomeTimeline( getTimelineHandler() );
    }

    public static TimelineFragment newInstance()
    {
        return new TimelineFragment();
    }
}
