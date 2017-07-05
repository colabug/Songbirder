package com.codepath.apps.songbirder.login;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.api.TwitterClient;
import com.codepath.apps.songbirder.TimelineActivity;
import com.codepath.oauth.OAuthLoginActionBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient>
{
    @BindView(R.id.pbProgressBar) ProgressBar pbProgressBar;

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

    // OAuth authenticated successfully, launch timeline
    @Override
    public void onLoginSuccess()
    {
        pbProgressBar.setVisibility( View.GONE );
        startActivity( TimelineActivity.newIntent( this ) );
    }

    // OAuth authentication flow failed, handle the error
    @Override
    public void onLoginFailure( Exception e )
    {
        pbProgressBar.setVisibility( View.GONE );
        e.printStackTrace();
    }

    // Start OAuth flow with the client
    @OnClick(R.id.btnLogin)
    public void loginToRest()
    {
        pbProgressBar.setVisibility( View.VISIBLE );
        getClient().connect();
    }
}
