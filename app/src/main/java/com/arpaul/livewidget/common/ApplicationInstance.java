package com.arpaul.livewidget.common;

import android.app.Application;

/**
 * Created by Aritra on 27-07-2016.
 */
public class ApplicationInstance extends Application {

    public static final int LOADER_FETCH_JSON_DATA      = 1;

    public static final String REST_URL                = "https://www.hackerearth.com/api/events/upcoming/?format=json";

    public static final String LOCK_APP_DB              = "LOCK_APP_DB";
    public static final String LOCK_CLICK               = "LOCK_CLICK";
}
