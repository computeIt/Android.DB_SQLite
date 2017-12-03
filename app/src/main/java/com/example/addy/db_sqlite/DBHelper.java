package com.example.addy.db_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Addy on 30.11.2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "contactDb";
    public static final String TABLE_CONTACTS = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_MAIL = "mail";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//invoke during creation of DB
        db.execSQL("create table " + TABLE_CONTACTS + "("
                                    + KEY_ID + " integer primary key,"
                                    + KEY_NAME + " text,"
                                    + KEY_MAIL + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//invoke if number version was changed
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);
    }
}
