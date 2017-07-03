package com.weslide.lovesmallscreen.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YY on 2017/4/24.
 */
public class MyOpenHelper extends SQLiteOpenHelper {

    private static int VERSION =1;
    private static String DBNAME = "records.db";
    public static String USERTABLE = "searchHistory";

    public MyOpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + USERTABLE + "(_id INTEGER PRIMARY KEY,NAME TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
