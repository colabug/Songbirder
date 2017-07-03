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
import com.codepath.apps.songbirder.listeners.ComposeTweetButtonListener;
import com.codepath.apps.songbirder.listeners.ComposeListener;
import com.codepath.apps.songbirder.views.ComposeTweetView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO: Display profile image with glide
public class ComposeTweetDialog extends DialogFragment implements ComposeTweetButtonListener
{
    private static final String ARG_REPLY_ID = "reply to id";
    private static final String ARG_REPLY_USERNAME = "reply to username";

    @BindView(R.id.ivClose) ImageView btnClose;
    @BindView(R.id.vComposeTweet) ComposeTweetView vComposeTweet;

    private ComposeListener listener;

    private long replyId = -1;

    public ComposeTweetDialog()
    {
    }

    public static ComposeTweetDialog newInstance()
    {
        return new ComposeTweetDialog();
    }

    public static ComposeTweetDialog newInstance( String username, long id )
    {
        ComposeTweetDialog dialog = new ComposeTweetDialog();

        Bundle args = new Bundle();
        args.putLong( ARG_REPLY_ID, id );
        args.putString( ARG_REPLY_USERNAME, username );
        dialog.setArguments( args );

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
    public View onCreateView( LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState )
    {
        View layout = inflater.inflate( R.layout.dialog_compose_tweet, container );
        ButterKnife.bind( this, layout );

        if( getArguments() != null )
        {
            replyId = getArguments().getLong( ARG_REPLY_ID );
            String username = getArguments().getString( ARG_REPLY_USERNAME );
            vComposeTweet.setReplyUsername( username );
        }

        showKeyboard();

        vComposeTweet.setTweetButtonListener( this );

        return layout;
    }

    public void setListener( ComposeListener listener )
    {
        this.listener = listener;
    }

    private void showKeyboard()
    {
        vComposeTweet.requestFocus();
        getDialog().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE );
    }

    @Override
    public void onTweetButtonClick( String tweetText )
    {
        submitTweetAndExit( tweetText );
    }

    @Override
    public void onEditorDone( String tweetText )
    {
        submitTweetAndExit( tweetText );
    }

    private void submitTweetAndExit( String tweetText )
    {
        listener.tweetComposed( tweetText, replyId );

        dismiss();
    }

    @OnClick(R.id.ivClose)
    void cancel()
    {
        dismiss();
    }
}
