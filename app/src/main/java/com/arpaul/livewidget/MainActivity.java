package com.arpaul.livewidget;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arpaul.livewidget.dataobject.HackathonDO;
import com.arpaul.livewidget.webservices.FetchDataService;
import com.arpaul.livewidget.webservices.WEBSERVICE_CALL;
import com.arpaul.livewidget.webservices.WEBSERVICE_TYPE;
import com.arpaul.livewidget.webservices.WebServiceResponse;

import java.util.ArrayList;

import static com.arpaul.livewidget.common.ApplicationInstance.LOADER_FETCH_JSON_DATA;
import static com.arpaul.livewidget.webservices.WebServiceResponse.FAILURE;
import static com.arpaul.livewidget.webservices.WebServiceResponse.SUCCESS;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private ProgressBar pbLoading;
    private TextView tvStatus;

    //http://stackoverflow.com/questions/42532821/how-to-parse-a-u-prefixed-json-response-in-android

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseControls();

        bindControls();
    }

    void bindControls() {
        getSupportLoaderManager().initLoader(LOADER_FETCH_JSON_DATA, null, this).forceLoad();
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        pbLoading.setVisibility(View.VISIBLE);
        switch (id){
            case LOADER_FETCH_JSON_DATA:
                return new FetchDataService(this, WEBSERVICE_TYPE.GET, WEBSERVICE_CALL.ALL);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        switch (loader.getId()){
            case LOADER_FETCH_JSON_DATA:
                ArrayList<HackathonDO> response = (ArrayList<HackathonDO>) data;
                if(response != null && response.size() > 0) {
                    tvStatus.setText("Success");
                } else {
                    tvStatus.setText("Failure");
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

        pbLoading.setVisibility(View.GONE);
    }
}
