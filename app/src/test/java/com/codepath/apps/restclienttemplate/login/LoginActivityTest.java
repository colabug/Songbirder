package com.codepath.apps.restclienttemplate.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.codepath.apps.restclienttemplate.BuildConfig;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.support.ResourceLocator;
import com.codepath.apps.restclienttemplate.timeline.TimelineActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static com.codepath.apps.restclienttemplate.support.Assert.assertViewIsVisible;
import static com.codepath.apps.restclienttemplate.support.ViewLocator.getButton;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.buildActivity;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest
{
    private LoginActivityStub activity;
    private Button loginButton;

    @Before
    public void setUp() throws Exception
    {
        activity = buildActivity( LoginActivityStub.class ).create().get();
        loginButton = getButton( activity, R.id.login_button );
    }

    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull( activity );
    }

    @Test
    public void shouldHaveLoginButton() throws Exception
    {
        assertViewIsVisible( loginButton );
        assertThat( loginButton.getText().toString(),
                    equalTo( ResourceLocator.getString( R.string.login_label ) ) );
    }

    @Test
    public void shouldLoginOnClick() throws Exception
    {
        loginButton.performClick();
        assertTrue( activity.loggedIn );
    }

    @Test
    public void shouldHandleLoginFailure() throws Exception
    {
        activity.loggedIn = true;
        activity.onLoginFailure( new Exception() );
        assertFalse( activity.loggedIn );
    }

    @Test
    public void shouldStartTimelineOnSuccess() throws Exception
    {
        activity.onLoginSuccess();

        ShadowActivity shadowActivity = shadowOf( activity );
        Intent intent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf( intent );
        assertThat( shadowIntent.getIntentClass().getCanonicalName(),
                    equalTo( TimelineActivity.class.getName() ) );
    }

    public static class LoginActivityStub extends LoginActivity
    {
        boolean loggedIn = false;

        @Override
        public void onLoginFailure( Exception e )
        {
            loggedIn = false;
        }

        @Override
        public void loginToRest( View view )
        {
            // Don't call Twitter API, record locally instead
            loggedIn = true;
        }
    }
}