package com.codepath.apps.songbirder.timeline;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
        switch( position )
        {
            case 0:
            case 1:
                TimelineFragment fragment = TimelineFragment.newInstance();
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