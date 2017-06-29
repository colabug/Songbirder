package com.codepath.apps.songbirder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.codepath.apps.songbirder.models.Tweet;

import org.parceler.Parcels;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComposeTweetDialog extends DialogFragment implements TextView.OnEditorActionListener
{
    private static final String ARG_TWEET = "tweet";

    private static final int MAX_TWEET_LENGTH = 140;
    private static final int WARNING_CHARACTER_COUNT = 10;

    @BindView(R.id.etEnterTweet) EditText etEnterTweet;
    @BindView(R.id.ivClose) ImageView btnClose;
    @BindView(R.id.tvCounter) TextView tvCounter;
    @BindView(R.id.btnTweet) Button btnTweet;

    @BindColor( R.color.red ) int red;
    @BindColor( R.color.dark_orange) int darkOrange;
    @BindColor( android.R.color.black ) int black;

    private Tweet tweet;

    private ComposeTweetDialogListener listener;

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
                etEnterTweet.setText( tweet.getDisplayUsername() + " " );
                // TODO: Display profile image with glide
            }
        }
        else
        {
            tweet = new Tweet();
        }

        configureKeyboard();

        listener = (ComposeTweetDialogListener) getActivity();

        return layout;
    }

    private void configureKeyboard()
    {
        showKeyboard();
        etEnterTweet.addTextChangedListener( getTextChangedListener() );
        etEnterTweet.setOnEditorActionListener( this );
    }

    private void showKeyboard()
    {
        etEnterTweet.requestFocus();
        getDialog().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE );
    }

    private TextWatcher getTextChangedListener()
    {
        return new TextWatcher()
        {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after )
            {

            }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count )
            {
                updateCounter();
            }

            @Override
            public void afterTextChanged( Editable s )
            {

            }
        };
    }

    private void updateCounter()
    {
        int currentLength = etEnterTweet.getText().length();
        int valueToDisplay = MAX_TWEET_LENGTH - currentLength;

        tvCounter.setTextColor( getTextColor( valueToDisplay ) );
        tvCounter.setText( String.valueOf( valueToDisplay ) );
    }

    private int getTextColor( int valueToDisplay )
    {
        if( valueToDisplay < 0 )
        {
            return red;
        }
        else if( valueToDisplay <= WARNING_CHARACTER_COUNT )
        {
            return darkOrange;
        }
        else
        {
            return black;
        }
    }

    @Override
    public boolean onEditorAction( TextView v, int actionId, KeyEvent event )
    {
        if( EditorInfo.IME_ACTION_DONE == actionId )
        {
            listener.onTweetSubmit( etEnterTweet.getText().toString(), tweet.getId() );

            dismiss();

            return true;
        }

        return false;
    }

    @OnClick(R.id.btnTweet)
    void submitTweet()
    {
        // Call the client or return the string to the activity
        listener.onTweetSubmit( etEnterTweet.getText().toString(), tweet.getId() );
        dismiss();
    }

    @OnClick(R.id.ivClose)
    void cancel()
    {
        dismiss();
    }
}
