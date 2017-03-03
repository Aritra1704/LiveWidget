package com.arpaul.livewidget.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by Aritra on 27-07-2016.
 */
public class ApplicationInstance  extends MultiDexApplication {

    public static final int LOADER_FETCH_JSON_DATA      = 1;
    public static final int LOADER_DELETE               = 2;
    public static final int LOADER_INSERT               = 3;
    public static final int LOADER_FETCH_DATA           = 4;

    public static final String REST_URL                = "https://www.hackerearth.com/api/events/upcoming/?format=xml";

    public static final String LOCK_APP_DB              = "LOCK_APP_DB";
    public static final String LOCK_CLICK               = "LOCK_CLICK";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
