package com.arpaul.livewidget.dataaccess;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

/**
 * Created by ARPaul on 07-01-2016.
 */
public class LWCPConstants {
    public static final String CONTENT_AUTHORITY = "com.arpaul.livewidget.dataaccess.ContentProviderHelper";

    public static final String DATABASE_NAME                    = "LiveWidget.sqlite";

    public static final String HACKTHON_TABLE_NAME              = "tblHackthon";

    public static final int DATABASE_VERSION                   = 1;

    public static final String PATH_RELATIONSHIP_JOIN          = "relationship_join";

    public static final String DELIMITER = "/";
    public static final String TABLE_ID    = "_id";

    public static final String TABLE_LEFT_OUTER_JOIN = " LEFT OUTER JOIN ";
    public static final String TABLE_ON = " ON ";
    public static final String TABLE_DOT = ".";
    public static final String TABLE_EQUAL = " = ";
    public static final String TABLE_AND = " AND ";
    public static final String TABLE_QUES  = " = ? ";
    public static final String TABLE_LIKE  = " LIKE ? ";
    public static final String TABLE_ORDER_BY  = " ORDER BY ";
    public static final String TABLE_DESC  = " DESC ";
    public static final String TABLE_FTTIME  = " strftime('%H %M', ";
    public static final String TABLE_IN_ENDBRACKET  = " ) ";

    public static final String CONTENT = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT + CONTENT_AUTHORITY);

    public static final Uri CONTENT_URI_HACKTHON = Uri.parse(CONTENT + CONTENT_AUTHORITY + DELIMITER + HACKTHON_TABLE_NAME);

    public static final Uri CONTENT_URI_RELATIONSHIP_JOIN = Uri.parse(CONTENT + CONTENT_AUTHORITY + DELIMITER + PATH_RELATIONSHIP_JOIN);

    public static final String PROVIDER_NAME = CONTENT_AUTHORITY;

    // create cursor of base type directory for multiple entries
    public static final String CONTENT_MULTIPLE_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + DELIMITER + CONTENT_AUTHORITY + DELIMITER + DATABASE_NAME;
    // create cursor of base type item for single entry
    public static final String CONTENT_BASE_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + DELIMITER + CONTENT_AUTHORITY + DELIMITER + DATABASE_NAME;

    public static Uri buildLocationUri(long id){
        return ContentUris.withAppendedId(CONTENT_URI_HACKTHON, id);
    }
}
