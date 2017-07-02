package com.codepath.apps.songbirder.timeline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.compose.ComposeTweetDialog;
import com.codepath.apps.songbirder.listeners.ComposeListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimelineActivity extends AppCompatActivity implements ComposeListener
{
    private static final String TAG = TimelineActivity.class.getSimpleName();

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;

    public static Intent newIntent( Context context )
    {
        return new Intent( context, TimelineActivity.class );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_timeline );

        ButterKnife.bind( this );

        setSupportActionBar( toolbar );

        getSupportFragmentManager().beginTransaction()
                                   .add( R.id.content, new TimelineFragment(), TimelineFragment.TAG )
                                   .commit();
    }

    // TODO: Get the user's profile image URL, send in here
    @OnClick(R.id.fab) void showComposeDialog()
    {
        ComposeTweetDialog dialog = ComposeTweetDialog.newInstance();
        dialog.show( getSupportFragmentManager(), TAG );
    }

    @Override
    public void startReply( String username, long replyId )
    {
        ComposeTweetDialog dialog = ComposeTweetDialog.newInstance( username, replyId );
        dialog.show( getSupportFragmentManager(), TAG );
    }

    // TODO: Do I need to break these up into separate listeners?
    @Override
    public void tweetComposed( String tweetText, long replyId )
    {
        // No op
    }
}
