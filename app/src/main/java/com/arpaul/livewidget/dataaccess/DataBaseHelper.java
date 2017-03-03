package com.arpaul.livewidget.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arpaul.livewidget.dataobject.HackathonDO;


/**
 * Created by ARPaul on 09-05-2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;


    static final String CREATE_SAVED_LOCATION_DB_TABLE =
            " CREATE TABLE IF NOT EXISTS " + LWCPConstants.HACKTHON_TABLE_NAME +
                    " (" + LWCPConstants.TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    HackathonDO.TITLE               + " VARCHAR NOT NULL, " +
                    HackathonDO.DESCRIPTION         + " VARCHAR, " +
                    HackathonDO.URL                 + " VARCHAR, " +
                    HackathonDO.SUBSCRIBE           + " VARCHAR, " +
                    HackathonDO.COLLEGE             + " VARCHAR, " +
                    HackathonDO.DATE                + " VARCHAR, " +
                    HackathonDO.TIME                + " VARCHAR, " +
                    HackathonDO.END_DATE            + " VARCHAR, " +
                    HackathonDO.END_TIME            + " VARCHAR, " +
                    HackathonDO.START_TIMESTAMP     + " VARCHAR, " +
                    HackathonDO.END_TIMESTAMP       + " VARCHAR, " +
                    HackathonDO.START_TZ            + " VARCHAR, " +
                    HackathonDO.END_TZ              + " VARCHAR, " +
                    HackathonDO.START_UTC_TZ        + " VARCHAR, " +
                    HackathonDO.END_UTC_TZ          + " VARCHAR, " +
                    HackathonDO.COVER_IMAGE         + " VARCHAR, " +
                    HackathonDO.THUMBNAIL           + " VARCHAR, " +
                    HackathonDO.IS_HACKEREARTH      + " VARCHAR, " +
                    HackathonDO.CHALLENGE_TYPE      + " VARCHAR " +
                    ");";

    DataBaseHelper(Context context){
        super(context, LWCPConstants.DATABASE_NAME, null, LWCPConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SAVED_LOCATION_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LWCPConstants.HACKTHON_TABLE_NAME);
        onCreate(db);
    }
}
