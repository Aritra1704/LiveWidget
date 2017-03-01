package com.arpaul.livewidget.parsers;

import android.content.Context;
import android.util.JsonReader;

import com.arpaul.livewidget.dataobject.HackathonDO;
import com.arpaul.livewidget.dataobject.ParserDO;
import com.arpaul.livewidget.webservices.WebServiceResponse;
import com.arpaul.utilitieslib.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by Aritra on 01-03-2017.
 */

public class JSONDataParser  {

    private WebServiceResponse response;
    private Context context;

    public static final String TAG_RESPONSE                 = "response";
    public static final String TAG_STATUS                   = "status";
    public static final String TAG_CHALLENGE_TYPE           = "challenge_type";
    public static final String TAG_START_TIMESTAMP          = "start_timestamp";

    public JSONDataParser(WebServiceResponse response, Context context) {
        this.response = response;
        this.context = context;
    }

    public ParserDO readHackathonJSONData(String data) {
        ParserDO parseDO = new ParserDO();

        ArrayList<HackathonDO> arrHackathonDO = null;
        HackathonDO objHackathonDO = null;
        try {
            byte[] utf8 = data.getBytes("UTF-8");

            data = new String(utf8, "UTF-8");
            JSONObject jsonMember = new JSONObject(data);
            if(JSONUtils.hasJSONtag(jsonMember, TAG_RESPONSE)) {
                JSONArray jsonResponse = jsonMember.getJSONArray(TAG_RESPONSE);
                if(jsonResponse != null && jsonResponse.length() > 0) {
                    arrHackathonDO = new ArrayList<>();
                    for(int i= 0; i < jsonResponse.length(); i++) {
                        JSONObject body = jsonResponse.getJSONObject(i);
                        objHackathonDO = new HackathonDO();
                        if(JSONUtils.hasJSONtag(body, TAG_STATUS)) {
                            objHackathonDO.status                = body.getString(TAG_STATUS);
                        }

                        if(JSONUtils.hasJSONtag(body, TAG_CHALLENGE_TYPE)) {
                            objHackathonDO.challenge_type             = body.getString(TAG_CHALLENGE_TYPE);
                        }

                        if(JSONUtils.hasJSONtag(body, TAG_START_TIMESTAMP)) {
                            objHackathonDO.start_timestamp             = body.getString(TAG_START_TIMESTAMP);
                        }

                        arrHackathonDO.add(objHackathonDO);
                    }
                }
            }

            if(arrHackathonDO != null)
                parseDO.linkHash.put(ParserDO.ParseType.TYPE_HACKATHON_DATA, arrHackathonDO);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return parseDO;
    }
}
