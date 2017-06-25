package com.codepath.apps.songbirder;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;


public class ComposeTweetBottomSheet extends BottomSheetDialogFragment
{
    private BottomSheetBehavior.BottomSheetCallback behaviorCallback = getBottomSheetCallback();

    @Override
    public void setupDialog( Dialog dialog, int style )
    {
        super.setupDialog( dialog, style );
        View contentView = View.inflate( getContext(), R.layout.fragment_bottom_sheet, null );
        dialog.setContentView( contentView );
        CoordinatorLayout.Behavior behavior = getLayoutParams( contentView ).getBehavior();
        if( behavior != null && behavior instanceof BottomSheetBehavior )
        {
            ( (BottomSheetBehavior) behavior ).setBottomSheetCallback( behaviorCallback );
        }
    }

    private CoordinatorLayout.LayoutParams getLayoutParams( View contentView )
    {
        return (CoordinatorLayout.LayoutParams) ( (View) contentView.getParent() ).getLayoutParams();
    }

    @NonNull
    private BottomSheetBehavior.BottomSheetCallback getBottomSheetCallback()
    {
        return new BottomSheetBehavior.BottomSheetCallback()
        {
            @Override
            public void onStateChanged( @NonNull View bottomSheet, int newState )
            {
                if( newState == BottomSheetBehavior.STATE_HIDDEN )
                {
                    dismiss();
                }
            }

            @Override
            public void onSlide( @NonNull View bottomSheet, float slideOffset )
            {
            }
        };
    }
}
