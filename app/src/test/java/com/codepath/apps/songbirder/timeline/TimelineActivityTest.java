package com.codepath.apps.songbirder.timeline;

import com.codepath.apps.songbirder.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;
import static org.robolectric.Robolectric.buildActivity;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TimelineActivityTest
{
    private TimelineActivityStub activity;

    @Before
    public void setUp() throws Exception
    {
        activity = buildActivity( TimelineActivityStub.class ).create().get();
    }

    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull( activity );
    }

    public static class TimelineActivityStub extends TimelineActivity
    {
        @Override
        protected void populateTimeline()
        {
            // Do nothing, don't call Twitter API
        }
    }
}