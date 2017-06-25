package com.codepath.apps.songbirder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComposeTweetDialog extends DialogFragment implements TextView.OnEditorActionListener
{
    public static final String PROFILE_URL_ARG = "profileURL";

    @BindView(R.id.etEnterTweet) EditText etEnterTweet;
    @BindView(R.id.ivCancelTweet) ImageView ivCancelTweet;
    @BindView(R.id.btnTweet) Button btnTweet;

    private ComposeTweetDialogListener listener;

    public interface ComposeTweetDialogListener
    {
        void onTweetEntered( String tweetText );
    }

    public ComposeTweetDialog()
    {
    }

    public static ComposeTweetDialog newInstance( String profileURL )
    {
        ComposeTweetDialog dialog = new ComposeTweetDialog();
        Bundle args = new Bundle();
        args.putString( PROFILE_URL_ARG, profileURL );
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
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View layout = inflater.inflate( R.layout.dialog_compose_tweet, container );
        ButterKnife.bind( this, layout );

        configureKeyboard();

        listener = (ComposeTweetDialogListener) getActivity();

        return layout;
    }

    private void configureKeyboard()
    {
        etEnterTweet.requestFocus();
        getDialog().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE );
        etEnterTweet.setOnEditorActionListener( this );
    }

    @Override
    public boolean onEditorAction( TextView v, int actionId, KeyEvent event )
    {
        if( EditorInfo.IME_ACTION_DONE == actionId )
        {
            listener.onTweetEntered( etEnterTweet.getText().toString() );
            dismiss();
            return true;
        }

        return false;
    }

    @OnClick(R.id.btnTweet)
    void submitTweet()
    {
        // Call the client or return the string to the activity
        listener.onTweetEntered( etEnterTweet.getText().toString() );
        dismiss();
    }

    @OnClick(R.id.ivCancelTweet)
    void cancel()
    {
        dismiss();
    }
}
