package com.hmdapp.finaltailor.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBMS extends SQLiteAssetHelper {

    final static String dbname= "newdb.db";
    public DBMS(Context context) {
        super(context, dbname,null, 1);
    }
}
