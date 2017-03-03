package com.arpaul.livewidget;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arpaul.livewidget.adapter.HackathonAdapter;
import com.arpaul.livewidget.dataaccess.InsertDataPref;
import com.arpaul.livewidget.dataaccess.InsertLoader;
import com.arpaul.livewidget.dataaccess.LWCPConstants;
import com.arpaul.livewidget.dataobject.HackathonDO;
import com.arpaul.livewidget.webservices.FetchDataService;
import com.arpaul.livewidget.webservices.WEBSERVICE_CALL;
import com.arpaul.livewidget.webservices.WEBSERVICE_TYPE;
import com.arpaul.livewidget.webservices.WebServiceResponse;
import com.arpaul.utilitieslib.NetworkUtility;

import java.util.ArrayList;

import static com.arpaul.livewidget.common.ApplicationInstance.LOADER_DELETE;
import static com.arpaul.livewidget.common.ApplicationInstance.LOADER_FETCH_DATA;
import static com.arpaul.livewidget.common.ApplicationInstance.LOADER_FETCH_JSON_DATA;
import static com.arpaul.livewidget.common.ApplicationInstance.LOADER_INSERT;
import static com.arpaul.livewidget.webservices.WebServiceResponse.FAILURE;
import static com.arpaul.livewidget.webservices.WebServiceResponse.SUCCESS;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private ProgressBar pbLoading;
    private TextView tvStatus;
    private RecyclerView rvList;

    private HackathonAdapter adapter;

    //http://stackoverflow.com/questions/42532821/how-to-parse-a-u-prefixed-json-response-in-android

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseControls();

        bindControls();
    }

    void bindControls() {
        getSupportLoaderManager().initLoader(LOADER_FETCH_DATA, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle bundle) {
        pbLoading.setVisibility(View.VISIBLE);
        tvStatus.setText("Loading data");
        switch (id){
            case LOADER_FETCH_JSON_DATA:
                return new FetchDataService(this, WEBSERVICE_TYPE.GET, WEBSERVICE_CALL.ALL);

            case LOADER_INSERT:
                return new InsertLoader(this, InsertDataPref.INSERT_HACKTHON, bundle);

            case LOADER_FETCH_DATA:
                return new CursorLoader(this, LWCPConstants.CONTENT_URI_HACKTHON,
                        new String[]{HackathonDO.TITLE, HackathonDO.DESCRIPTION, HackathonDO.STATUS, HackathonDO.COLLEGE,
                                HackathonDO.THUMBNAIL, HackathonDO.CHALLENGE_TYPE, HackathonDO.URL},
                        null,
                        null,
                        null);

            default:
                return null;
        }
    }

    ArrayList<HackathonDO> arrHackthon;
    @Override
    public void onLoadFinished(Loader loader, Object data) {
        switch (loader.getId()){
            case LOADER_FETCH_JSON_DATA:
                arrHackthon = (ArrayList<HackathonDO>) data;
                if(arrHackthon != null && arrHackthon.size() > 0) {
                    adapter.refresh(arrHackthon);
                    rvList.setVisibility(View.VISIBLE);
                    tvStatus.setVisibility(View.GONE);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(InsertLoader.BUNDLE_INSERTLOADER, arrHackthon);
                    if(getSupportLoaderManager().getLoader(LOADER_INSERT) == null)
                        getSupportLoaderManager().initLoader(LOADER_INSERT, bundle, this);
                    else
                        getSupportLoaderManager().restartLoader(LOADER_INSERT, bundle, this);
                } else {
                    tvStatus.setText("Failure");
                    rvList.setVisibility(View.GONE);
                }
                break;

            case LOADER_INSERT:
                if(data instanceof String) {
                    String status = (String) data;
                    if(status.equalsIgnoreCase("Success")) {
                        if(getSupportLoaderManager().getLoader(LOADER_FETCH_DATA) == null)
                            getSupportLoaderManager().initLoader(LOADER_FETCH_DATA, null, this);
                        else
                            getSupportLoaderManager().restartLoader(LOADER_FETCH_DATA, null, this);
                    }
                }
                break;

            case LOADER_FETCH_DATA:
                if(data instanceof Cursor) {
                    Cursor cursor = (Cursor) data;
                    if(cursor != null && cursor.moveToFirst()){
                        HackathonDO objActiRecogDO = null;
                        if(arrHackthon != null && arrHackthon.size() > 0)
                            arrHackthon.clear();
                        else
                            arrHackthon = new ArrayList<>();
                        do{
                            objActiRecogDO = new HackathonDO();
                            objActiRecogDO.title = cursor.getString(cursor.getColumnIndex(HackathonDO.TITLE));
                            objActiRecogDO.description = cursor.getString(cursor.getColumnIndex(HackathonDO.DESCRIPTION));
                            objActiRecogDO.status = cursor.getString(cursor.getColumnIndex(HackathonDO.STATUS));
                            objActiRecogDO.college = cursor.getString(cursor.getColumnIndex(HackathonDO.COLLEGE));
                            objActiRecogDO.thumbnail = cursor.getString(cursor.getColumnIndex(HackathonDO.THUMBNAIL));
                            objActiRecogDO.challenge_type = cursor.getString(cursor.getColumnIndex(HackathonDO.CHALLENGE_TYPE));
                            objActiRecogDO.url = cursor.getString(cursor.getColumnIndex(HackathonDO.URL));
                            arrHackthon.add(objActiRecogDO);
                        } while(cursor.moveToNext());
                        cursor.close();
                        adapter.refresh(arrHackthon);
                    } else {
                        if(NetworkUtility.isConnectionAvailable(this))
                            getSupportLoaderManager().initLoader(LOADER_FETCH_JSON_DATA, null, this).forceLoad();
                        else
                            tvStatus.setText("No internet connection.");
                    }
                }
                break;
        }
        getSupportLoaderManager().destroyLoader(loader.getId());
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    void initialiseControls() {
        pbLoading   = (ProgressBar) findViewById(R.id.pbLoading);
        tvStatus    = (TextView) findViewById(R.id.tvStatus);
        rvList      = (RecyclerView) findViewById(R.id.rvList);

        adapter = new HackathonAdapter(this, new ArrayList<HackathonDO>());
        rvList.setAdapter(adapter);
        pbLoading.setVisibility(View.GONE);
    }
}
