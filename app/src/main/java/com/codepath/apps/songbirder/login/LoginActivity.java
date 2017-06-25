package com.codepath.apps.songbirder.login;

import android.os.Bundle;
import android.view.Menu;

import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.api.TwitterClient;
import com.codepath.apps.songbirder.timeline.TimelineActivity;
import com.codepath.oauth.OAuthLoginActionBarActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient>
{
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        ButterKnife.bind( this );
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.login, menu );
        return true;
    }

    // OAuth authenticated successfully, launch primary authenticated activity
    @Override
    public void onLoginSuccess()
    {
        startActivity( TimelineActivity.newIntent( this ) );
    }

    // OAuth authentication flow failed, handle the error
    // i.e Display an error dialog or toast
    @Override
    public void onLoginFailure( Exception e )
    {
        e.printStackTrace();
    }

    // Start OAuth flow with the client
    @OnClick(R.id.btnLogin)
    public void loginToRest()
    {
        getClient().connect();
    }
}
