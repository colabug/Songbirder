package com.codepath.apps.songbirder.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.songbirder.R;

import butterknife.ButterKnife;

public class TweetDetailActivity extends AppCompatActivity
{
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );

        ButterKnife.bind( this );
    }
}
