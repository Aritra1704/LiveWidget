package com.arpaul.livewidget.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.arpaul.livewidget.R;
import com.arpaul.livewidget.dataaccess.LWCPConstants;
import com.arpaul.livewidget.dataobject.HackathonDO;

import java.util.ArrayList;

/**
 * Created by ARPaul on 26-11-2016.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList listItemList = new ArrayList();
    private Context context = null;
    private int appWidgetId;
    private static final int CURSOR_LOADER_ID = 1;
    //private QuoteCursorAdapter mCursorAdapter;
    //private Cursor mCursor;
    private ArrayList<HackathonDO> arrHackathonDO = new ArrayList<>();

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        loadData();
    }

    @Override
    public void onDataSetChanged() {
        loadData();
    }

    private Thread thread;
    private void loadData(){
        thread = new Thread() {
            public void run() {
                query();
            }
        };
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
        }
    }

    private void query(){
        Cursor cursor = context.getContentResolver().query(LWCPConstants.CONTENT_URI_HACKTHON,
                new String[]{HackathonDO.TITLE, HackathonDO.DESCRIPTION, HackathonDO.STATUS, HackathonDO.COLLEGE},
                null,
                null,
                null);

        if(cursor != null && cursor.moveToFirst()){
            HackathonDO objActiRecogDO = null;
            do{
                objActiRecogDO = new HackathonDO();
                objActiRecogDO.title = cursor.getString(cursor.getColumnIndex(HackathonDO.TITLE));
                objActiRecogDO.description = cursor.getString(cursor.getColumnIndex(HackathonDO.DESCRIPTION));
                objActiRecogDO.status = cursor.getString(cursor.getColumnIndex(HackathonDO.STATUS));
                objActiRecogDO.college = cursor.getString(cursor.getColumnIndex(HackathonDO.COLLEGE));
                arrHackathonDO.add(objActiRecogDO);
            } while(cursor.moveToNext());

            cursor.close();
        }
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return arrHackathonDO.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
    *Similar to getView of Adapter where instead of View
    *we return RemoteViews
    *
    */
    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.cell_widgetlist);
        HackathonDO objGeoFenceLocDO = arrHackathonDO.get(position);
        remoteView.setTextViewText(R.id.tvTitle, objGeoFenceLocDO.title);
        remoteView.setTextViewText(R.id.tvDescription, objGeoFenceLocDO.description);

        remoteView.setTextViewText(R.id.tvStatus, objGeoFenceLocDO.status);
        remoteView.setTextViewText(R.id.tvCollege, objGeoFenceLocDO.college);

        return remoteView;
    }
}
