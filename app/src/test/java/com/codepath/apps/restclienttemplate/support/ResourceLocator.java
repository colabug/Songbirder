package com.codepath.apps.restclienttemplate.support;

import org.robolectric.RuntimeEnvironment;

public class ResourceLocator
{
    public static String getString(int stringId)
    {
        return RuntimeEnvironment.application.getString(stringId);
    }
}