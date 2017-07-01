package com.codepath.apps.songbirder;

import android.app.Dialog;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.codepath.apps.songbirder.compose.ComposeTweetDialog;
import com.codepath.apps.songbirder.support.ActivityWithListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;

import static com.codepath.apps.songbirder.support.Assert.assertViewIsVisible;
import static com.codepath.apps.songbirder.support.ViewLocator.getButton;
import static com.codepath.apps.songbirder.support.ViewLocator.getEditText;
import static com.codepath.apps.songbirder.support.ViewLocator.getImageView;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ComposeTweetDialogTest
{
    private static final String PROFILE_URL = "http://twitter.com/image.url";

    private ActivityWithListener activity;
    private ComposeTweetDialog dialog;

    // View
    private EditText tweetEntry;
    private Button tweetButton;
    private ImageView closeButton;
    private ImageView profileImage;

    @Before
    public void setUp() throws Exception
    {
        activity = Robolectric.setupActivity( ActivityWithListener.class );
        dialog = ComposeTweetDialog.newInstance( PROFILE_URL, tweet );
        dialog.show( activity.getSupportFragmentManager(), "tag" );

        tweetEntry = getEditText( dialog, R.id.etEnterTweet );
        tweetButton = getButton( dialog, R.id.btnTweet );
        closeButton = getImageView( dialog, R.id.ivClose );
        profileImage = getImageView( dialog, R.id.ivProfileImage );
    }

    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull( dialog );
    }

    @Test
    public void shouldShowDialog() throws Exception
    {
        assertTrue(dialog.isVisible());
    }

    @Test
    public void shouldHaveTweetEntry() throws Exception
    {
        assertViewIsVisible( tweetEntry );
    }

    @Test
    public void shouldHaveCloseButton() throws Exception
    {
        assertViewIsVisible( closeButton );
    }

    @Test
    public void shouldHaveProfileImage() throws Exception
    {
        assertViewIsVisible( profileImage );
    }

    @Test
    public void shouldHaveTweetButton() throws Exception
    {
        assertViewIsVisible( tweetButton );
    }

    @Test
    public void closeButtonShouldDismiss() throws Exception
    {
        closeButton.performClick();

        assertFalse( getShownDialog().isShowing() );
    }

    @Test
    public void tweetButtonShouldTweetAndDismiss() throws Exception
    {
        tweetButton.performClick();

        assertThat( tweetEntry.getText().toString(), equalTo( activity.tweetText ) );
        assertFalse( getShownDialog().isShowing() );
    }

    @Test
    public void keyboardDoneShouldTweetAndDismiss() throws Exception
    {
        tweetEntry.onEditorAction( EditorInfo.IME_ACTION_DONE );

        assertThat( tweetEntry.getText().toString(), equalTo( activity.tweetText ) );

        assertFalse( getShownDialog().isShowing() );
    }

    @Test
    public void keyboardOtherThanDoneShouldNotTweet() throws Exception
    {
        tweetEntry.onEditorAction( EditorInfo.IME_ACTION_NEXT );
        tweetEntry.onEditorAction( EditorInfo.IME_ACTION_NEXT );

        assertNull( activity.tweetText );
        assertTrue( getShownDialog().isShowing() );
    }

    private Dialog getShownDialog()
    {
        return ShadowDialog.getLatestDialog();
    }
}