package com.codepath.apps.songbirder.timeline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.codepath.apps.songbirder.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimelineActivity extends AppCompatActivity
{
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private TimelineFragment fragment;

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

        fragment = new TimelineFragment();

        getSupportFragmentManager().beginTransaction()
                                   .add( R.id.content, fragment, TimelineFragment.TAG )
                                   .commit();
    }

    // TODO: Get the user's profile image URL, send in here
    @OnClick(R.id.fab) void showComposeDialog()
    {
        fragment.showDialog();
    }
}
