package com.arpaul.livewidget.webservices;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import com.arpaul.livewidget.dataobject.HackathonDO;
import com.arpaul.livewidget.dataobject.ParserDO;
import com.arpaul.livewidget.parsers.JSONDataParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.arpaul.livewidget.common.ApplicationInstance.REST_URL;

/**
 * Created by Aritra on 01-08-2016.
 */
public class FetchDataService extends AsyncTaskLoader {

    private Context context;
    private String param;
    private WEBSERVICE_TYPE type;
    private WEBSERVICE_CALL call;
    private ArrayList<HackathonDO> response;
    private ParserDO objParseDO;

    public FetchDataService(Context context, WEBSERVICE_TYPE type, WEBSERVICE_CALL call){
        super(context);
        this.context = context;
        this.type = type;
        this.call = call;
    }

    @Override
    public ArrayList<HackathonDO> loadInBackground() {

        switch (call){
            default:
                response = assignGroupLead(context);
                break;

        }
        return response;
    }

    public ArrayList<HackathonDO> assignGroupLead(Context context){

        ArrayList<HackathonDO> arrHackathonDO = new ArrayList<>();
        final WebServiceResponse response = new RestServiceCalls(REST_URL, null, null, type).getData();

        ParserDO parseDO = new JSONDataParser(response, context).readHackathonJSONData(response.getResponseMessage());
        if(parseDO.linkHash.containsKey(ParserDO.ParseType.TYPE_HACKATHON_DATA)) {
            arrHackathonDO = (ArrayList<HackathonDO>) parseDO.linkHash.get(ParserDO.ParseType.TYPE_HACKATHON_DATA);
        }
        return arrHackathonDO;
    }
}
