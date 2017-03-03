package com.arpaul.livewidget.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.arpaul.livewidget.R;
import com.arpaul.livewidget.dataaccess.LWCPConstants;
import com.arpaul.livewidget.dataobject.HackathonDO;
import com.arpaul.utilitieslib.ColorUtils;

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
        final long identityToken = Binder.clearCallingIdentity();

        loadData();

        Binder.restoreCallingIdentity(identityToken);
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
                HackathonDO.STATUS + LWCPConstants.TABLE_QUES,
                new String[]{"ONGOING"},
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

            if(cursor != null && !cursor.isClosed())
                cursor.close();


        } else if(cursor != null)
            cursor.close();
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
        HackathonDO objHackathonDO = arrHackathonDO.get(position);
        remoteView.setTextViewText(R.id.tvTitle, objHackathonDO.title);
        remoteView.setTextViewText(R.id.tvDescription, objHackathonDO.description);

        remoteView.setTextViewText(R.id.tvStatus, objHackathonDO.status);
        StringBuilder college = new StringBuilder();
        college.append("College: ");
        if(objHackathonDO.college.equalsIgnoreCase("true"))
            college.append("Yes");
        else
            college.append("No");
        remoteView.setTextViewText(R.id.tvCollege, college.toString());

        return remoteView;
    }
}
