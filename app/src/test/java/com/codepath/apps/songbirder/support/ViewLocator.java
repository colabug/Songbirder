package com.codepath.apps.songbirder.support;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewLocator
{
    public static Button getButton( Activity activity, int viewId)
    {
        return (Button) activity.findViewById(viewId);
    }

    public static Button getButton( Fragment fragment, int viewId)
    {
        return (Button) fragment.getView().findViewById(viewId);
    }

    public static TextView getTextView(Activity activity, int viewId)
    {
        return (TextView) activity.findViewById(viewId);
    }

    public static EditText getEditText( Fragment fragment, int viewId)
    {
        return (EditText) fragment.getView().findViewById(viewId);
    }

    public static ImageView getImageView( Activity activity, int viewId)
    {
        return (ImageView) activity.findViewById( viewId);
    }
    public static ImageView getImageView( Fragment fragment, int viewId)
    {
        return (ImageView) fragment.getView().findViewById(viewId);
    }
}