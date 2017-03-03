package com.arpaul.livewidget.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.arpaul.livewidget.dataobject.HackathonDO;
import com.arpaul.utilitieslib.LogUtils;

import java.util.ArrayList;

import static com.arpaul.livewidget.dataaccess.InsertDataPref.DELETE_ALL_DATA;
import static com.arpaul.livewidget.dataaccess.InsertDataPref.INSERT_HACKTHON;

/**
 * Created by ARPaul on 03-03-2017.
 */

public class InsertLoader extends AsyncTaskLoader {
    private Context context;
    private Object data;

    private final String TAG = "InsertLoader";
    private int dataPref;

    public final static String BUNDLE_INSERTLOADER      = "BUNDLE_INSERTLOADER";

    /**
     *
     * @param context
     */
    public InsertLoader(Context context){
        super(context);
        onContentChanged();
        this.context = context;
    }

    /**
     *
     * @param context
     * @param dataPref
     * @param bundle
     */
    public InsertLoader(Context context, int dataPref, Bundle bundle){
        super(context);
        onContentChanged();
        this.context = context;
        if(bundle != null)
            data = bundle.get(BUNDLE_INSERTLOADER);

        this.dataPref = dataPref;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
    }

    @Override
    public Object loadInBackground() {

        @InsertDataPref.InsertDataPreference int dataPreference = dataPref;
        switch (dataPreference) {
            case INSERT_HACKTHON:
                if(data != null){
                    ArrayList<HackathonDO> arrHackthon = (ArrayList<HackathonDO>) data;
                    int insertData = 0;
                    String address = "Failure";
                    if(arrHackthon != null && arrHackthon.size() > 0) {
                        ContentValues[] contentValues = new ContentValues[arrHackthon.size()];
                        for(int i = 0; i < arrHackthon.size(); i++) {
                            HackathonDO objHackathonDO = arrHackthon.get(i);
                            contentValues[i] = new ContentValues();
                            contentValues[i].put(HackathonDO.TITLE, objHackathonDO.title);
                            contentValues[i].put(HackathonDO.DESCRIPTION, objHackathonDO.description);
                            contentValues[i].put(HackathonDO.STATUS, objHackathonDO.status);
                            contentValues[i].put(HackathonDO.URL, objHackathonDO.url);
                            contentValues[i].put(HackathonDO.SUBSCRIBE, objHackathonDO.subscribe);
                            contentValues[i].put(HackathonDO.COLLEGE, objHackathonDO.college);
                            contentValues[i].put(HackathonDO.DATE, objHackathonDO.date);
                            contentValues[i].put(HackathonDO.TIME, objHackathonDO.time);
                            contentValues[i].put(HackathonDO.END_DATE, objHackathonDO.end_date);
                            contentValues[i].put(HackathonDO.END_TIME, objHackathonDO.end_time);
                            contentValues[i].put(HackathonDO.START_TIMESTAMP, objHackathonDO.start_timestamp);
                            contentValues[i].put(HackathonDO.END_TIMESTAMP, objHackathonDO.end_timestamp);
                            contentValues[i].put(HackathonDO.START_TZ, objHackathonDO.start_tz);
                            contentValues[i].put(HackathonDO.END_TZ, objHackathonDO.end_tz);
                            contentValues[i].put(HackathonDO.START_UTC_TZ, objHackathonDO.start_utc_tz);
                            contentValues[i].put(HackathonDO.END_UTC_TZ, objHackathonDO.end_utc_tz);
                            contentValues[i].put(HackathonDO.COVER_IMAGE, objHackathonDO.cover_image);
                            contentValues[i].put(HackathonDO.THUMBNAIL, objHackathonDO.thumbnail);
                            contentValues[i].put(HackathonDO.IS_HACKEREARTH, objHackathonDO.is_hackerearth);
                            contentValues[i].put(HackathonDO.CHALLENGE_TYPE, objHackathonDO.challenge_type);

                        }
                        insertData = context.getContentResolver().bulkInsert(LWCPConstants.CONTENT_URI_HACKTHON, contentValues);
                    }
                    if(insertData > 0)
                        address = "Success";
                    return address;
                }
                break;

            case DELETE_ALL_DATA:
                String address = "Failure";
                int deleteData = context.getContentResolver().delete(LWCPConstants.CONTENT_URI_HACKTHON, null, null);
                if(deleteData > 0)
                    address = "Success";
                return address;
            default:

                break;
        }

        return null;
    }

    @Override
    protected void onStopLoading() {
        if (LogUtils.isLogEnable)
            Log.i(TAG, "+++ onStopLoading() called! +++");

        cancelLoad();
    }

    @Override
    protected void onReset() {
        if (LogUtils.isLogEnable)
            Log.i(TAG, "+++ onReset() called! +++");

        // Ensure the loader is stopped.
        onStopLoading();

    }

    @Override
    public void forceLoad() {
        if (LogUtils.isLogEnable)
            Log.i(TAG, "+++ forceLoad() called! +++");
        super.forceLoad();
    }
}
