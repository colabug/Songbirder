package com.codepath.apps.songbirder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.songbirder.timeline.MentionsFragment;
import com.codepath.apps.songbirder.timeline.TimelineFragment;

public class PagerAdapter extends FragmentPagerAdapter
{
    public static final int FRAGMENT_COUNT = 2;

    public PagerAdapter( FragmentManager fm )
    {
        super( fm );
    }

    @Override
    public Fragment getItem( int position )
    {
        BaseFragment fragment;

        switch( position )
        {
            case 0:
                fragment = TimelineFragment.newInstance();
                fragment.setPosition( position );
                return fragment;
            case 1:
                fragment = MentionsFragment.newInstance();
                fragment.setPosition( position );
                return fragment;

        }
        return null;
    }

    @Override
    public int getCount()
    {
        return FRAGMENT_COUNT;
    }
}