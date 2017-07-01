package com.codepath.apps.songbirder.compose;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.models.Tweet;
import com.codepath.apps.songbirder.views.ComposeTweetView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComposeTweetDialog extends DialogFragment implements ComposeTweetView.TweetButtonListener
{
    private static final String ARG_TWEET = "tweet";

    @BindView(R.id.ivClose) ImageView btnClose;
    @BindView(R.id.vComposeTweet) ComposeTweetView vComposeTweet;

    private Tweet tweet;

    private ComposeTweetDialogListener listener;

    // TODO: Centralize with other listener
    public interface ComposeTweetDialogListener
    {
        void onTweetSubmit( String tweetText, long replyId );
    }

    public ComposeTweetDialog()
    {
    }

    public static ComposeTweetDialog newInstance( Tweet tweet )
    {
        ComposeTweetDialog dialog = new ComposeTweetDialog();

        if (tweet != null)
        {
            Bundle args = new Bundle();
            args.putParcelable( ARG_TWEET, Parcels.wrap( tweet ) );
            dialog.setArguments( args );
        }

        return dialog;
    }

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        // Fill screen, no actionbar or title
        setStyle( DialogFragment.STYLE_NO_TITLE, android.R.style.Theme );
    }

    @Override
    @SuppressLint("SetTextI18n")
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View layout = inflater.inflate( R.layout.dialog_compose_tweet, container );
        ButterKnife.bind( this, layout );

        if( getArguments() != null )
        {
            tweet = Parcels.unwrap( getArguments().getParcelable( ARG_TWEET ) );
            if( tweet != null )
            {
                vComposeTweet.setTweetText( tweet.getDisplayUsername() + " " );
                // TODO: Display profile image with glide
            }
        }
        else
        {
            tweet = new Tweet();
        }

        showKeyboard();

        listener = (ComposeTweetDialogListener) getActivity();
        vComposeTweet.setListener( this );

        return layout;
    }

    private void showKeyboard()
    {
        vComposeTweet.requestFocus();
        getDialog().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE );
    }

    @Override
    public void onTweetButtonClick()
    {
        submitTweetAndExit();
    }

    @Override
    public void onEditorDone()
    {
        submitTweetAndExit();
    }

    private void submitTweetAndExit()
    {
        listener.onTweetSubmit( vComposeTweet.getTweetText(), tweet.getId() );

        dismiss();
    }

    @OnClick(R.id.ivClose)
    void cancel()
    {
        dismiss();
    }
}
