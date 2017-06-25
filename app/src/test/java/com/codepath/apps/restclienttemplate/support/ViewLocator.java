package com.codepath.apps.restclienttemplate.support;

import android.app.Activity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewLocator
{
    public static Button getButton( Activity activity, int viewId)
    {
        return (Button) activity.findViewById(viewId);
    }

    public static TextView getTextView(Activity activity, int viewId)
    {
        return (TextView) activity.findViewById(viewId);
    }

    public static ImageView getImageView( Activity activity, int viewId)
    {
        return (ImageView) activity.findViewById( viewId);
    }
}