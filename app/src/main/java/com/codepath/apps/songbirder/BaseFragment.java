package com.codepath.apps.songbirder;

import android.support.v4.app.Fragment;

import com.codepath.apps.songbirder.compose.ComposeTweetDialog;
import com.codepath.apps.songbirder.listeners.ComposeListener;
import com.codepath.apps.songbirder.timeline.TimelineFragment;

public class BaseFragment extends Fragment implements ComposeListener
{
    public static final String TAG = TimelineFragment.class.getSimpleName();

    private int position;

    public static final int COMPOSE_REQUEST_CODE = 20;

    public void setPosition( int position )
    {
        this.position = position;
    }

    public int getPosition()
    {
        return position;
    }

    public void showDialog()
    {
        ComposeTweetDialog dialog = ComposeTweetDialog.newInstance();
        dialog.setListener( this );
        dialog.setTargetFragment( this, COMPOSE_REQUEST_CODE );
        dialog.show( getActivity().getSupportFragmentManager(), TAG );
    }

    @Override
    public void tweetComposed( String tweetText, long replyId )
    {
        // No op
    }
}
