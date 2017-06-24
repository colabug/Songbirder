package com.codepath.apps.restclienttemplate.login;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.api.TwitterClient;
import com.codepath.apps.restclienttemplate.timeline.TimelineActivity;
import com.codepath.oauth.OAuthLoginActionBarActivity;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient>
{
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
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

    // Click handler method for the button used to start OAuth flow
    // Uses the client to initiate OAuth authorization
    // This should be tied to a button used to login
    public void loginToRest( View view )
    {
        getClient().connect();
    }
}
