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
    public static final String TAG_TITLE                    = "title";
    public static final String TAG_DESCRIPTION              = "description";
    public static final String TAG_URL                      = "url";
    public static final String TAG_SUBSCRIBE                = "subscribe";
    public static final String TAG_COLLEGE                  = "college";
    public static final String TAG_DATE                     = "date";
    public static final String TAG_TIME                     = "time";
    public static final String TAG_END_DATE                 = "end_date";
    public static final String TAG_END_TIME                 = "end_time";
    public static final String TAG_START_TIMESTAMP          = "start_timestamp";
    public static final String TAG_END_TIMESTAMP            = "end_timestamp";
    public static final String TAG_START_TZ                 = "start_tz";
    public static final String TAG_END_TZ                   = "end_tz";
    public static final String TAG_START_UTC_TZ             = "start_utc_tz";
    public static final String TAG_END_UTC_TZ               = "end_utc_tz";
    public static final String TAG_COVER_IMAGE              = "cover_image";
    public static final String TAG_THUMBNAIL                = "thumbnail";
    public static final String TAG_IS_HACKEREARTH           = "is_hackerearth";
    public static final String TAG_CHALLENGE_TYPE           = "challenge_type";


    public JSONDataParser(WebServiceResponse response, Context context) {
        this.response = response;
        this.context = context;
    }

    public ParserDO readHackathonJSONData(String data) {
        ParserDO parseDO = new ParserDO();

        ArrayList<HackathonDO> arrHackathonDO = null;
        HackathonDO objHackathonDO = null;
        try {
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

                        if(JSONUtils.hasJSONtag(body, TAG_TITLE))
                            objHackathonDO.title             = body.getString(TAG_TITLE);

                        if(JSONUtils.hasJSONtag(body, TAG_DESCRIPTION))
                            objHackathonDO.description             = body.getString(TAG_DESCRIPTION);

                        if(JSONUtils.hasJSONtag(body, TAG_URL))
                            objHackathonDO.url             = body.getString(TAG_URL);

                        if(JSONUtils.hasJSONtag(body, TAG_SUBSCRIBE))
                            objHackathonDO.subscribe             = body.getString(TAG_SUBSCRIBE);

                        if(JSONUtils.hasJSONtag(body, TAG_COLLEGE))
                            objHackathonDO.college             = body.getString(TAG_COLLEGE);

                        if(JSONUtils.hasJSONtag(body, TAG_DATE))
                            objHackathonDO.date             = body.getString(TAG_DATE);

                        if(JSONUtils.hasJSONtag(body, TAG_TIME))
                            objHackathonDO.time             = body.getString(TAG_TIME);

                        if(JSONUtils.hasJSONtag(body, TAG_END_DATE))
                            objHackathonDO.end_date             = body.getString(TAG_END_DATE);

                        if(JSONUtils.hasJSONtag(body, TAG_END_TIME))
                            objHackathonDO.end_time             = body.getString(TAG_END_TIME);

                        if(JSONUtils.hasJSONtag(body, TAG_END_TIMESTAMP))
                            objHackathonDO.end_timestamp             = body.getString(TAG_END_TIMESTAMP);

                        if(JSONUtils.hasJSONtag(body, TAG_START_TZ))
                            objHackathonDO.start_tz             = body.getString(TAG_START_TZ);

                        if(JSONUtils.hasJSONtag(body, TAG_END_TZ))
                            objHackathonDO.end_tz             = body.getString(TAG_END_TZ);

                        if(JSONUtils.hasJSONtag(body, TAG_START_UTC_TZ))
                            objHackathonDO.start_utc_tz             = body.getString(TAG_START_UTC_TZ);

                        if(JSONUtils.hasJSONtag(body, TAG_END_UTC_TZ))
                            objHackathonDO.end_utc_tz             = body.getString(TAG_END_UTC_TZ);

                        if(JSONUtils.hasJSONtag(body, TAG_COVER_IMAGE))
                            objHackathonDO.cover_image             = body.getString(TAG_COVER_IMAGE);

                        if(JSONUtils.hasJSONtag(body, TAG_THUMBNAIL))
                            objHackathonDO.thumbnail             = body.getString(TAG_THUMBNAIL);

                        if(JSONUtils.hasJSONtag(body, TAG_IS_HACKEREARTH))
                            objHackathonDO.is_hackerearth             = body.getString(TAG_IS_HACKEREARTH);

                        if(JSONUtils.hasJSONtag(body, TAG_START_TIMESTAMP))
                            objHackathonDO.start_timestamp             = body.getString(TAG_START_TIMESTAMP);

                        if(JSONUtils.hasJSONtag(body, TAG_CHALLENGE_TYPE))
                            objHackathonDO.challenge_type             = body.getString(TAG_CHALLENGE_TYPE);

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
