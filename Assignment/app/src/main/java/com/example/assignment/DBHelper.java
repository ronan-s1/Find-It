package com.example.assignment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FindIt";

    public static final String ADMIN_TABLE = "admins";
    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String ITEMS_TABLE = "items";
    public static final String ITEM_ID = "item_id";
    public static final String NAME = "name";
    public static final String STUDENT_NO = "student_no";
    public static final String ITEM = "item";
    public static final String DESCRIPTION = "description";
    public static final String PHONE = "phone";
    public static final String LOCATION = "location";
    public static final String POST_TIME = "post_time";
    public static final String LF = "LF";

    public static final String CREATE_ITEMS_TABLE =
            "CREATE TABLE " + ITEMS_TABLE + " (" +
            ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT NOT NULL, " +
            STUDENT_NO + " TEXT NOT NULL, " +
            ITEM + " TEXT NOT NULL, " +
            DESCRIPTION + " TEXT NOT NULL, " +
            PHONE + " TEXT NOT NULL, " +
            LOCATION + " TEXT NOT NULL, " +
            POST_TIME + " TEXT NOT NULL, " +
            LF + " TEXT NOT NULL);";

    public static final String CREATE_ADMINS_TABLE =
            "CREATE TABLE " + ADMIN_TABLE + " (" +
            USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USERNAME + " TEXT NOT NULL, " +
            PASSWORD + " TEXT NOT NULL);";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ITEMS_TABLE);
        db.execSQL(CREATE_ADMINS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + ITEMS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ADMIN_TABLE);
        onCreate(db);
    }
}