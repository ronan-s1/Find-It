package com.example.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    Context context;
    private DBHelper DBhelper;
    private SQLiteDatabase DB;

    public DBManager(Context context) {
        this.context = context;
    }

    public DBManager open() throws SQLException {
        DBhelper = new DBHelper(context);
        DB = DBhelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBhelper.close();
    }

    //https://developer.android.com/reference/android/database/DatabaseUtils
    public void insertAdmin(){
        if (DatabaseUtils.queryNumEntries(DB, DBHelper.ADMIN_TABLE) == 0) {
            ContentValues contentValuesAdmins = new ContentValues();
            contentValuesAdmins.put(DBHelper.USERNAME, "admin");
            contentValuesAdmins.put(DBHelper.PASSWORD, "123");
            DB.insert(DBHelper.ADMIN_TABLE, null, contentValuesAdmins);
        }
    }

    //selecting everything from the table
    public Cursor getAllItems() {
        String selectLostQuery = "SELECT item_id, item, name, description, student_no, phone, location, post_time, LF FROM " + DBHelper.ITEMS_TABLE;
        return DB.rawQuery(selectLostQuery, null);
    }

    //selecting all records with "L" in the LF field
    public Cursor getLostItems() {
        String selectLostQuery = "SELECT item_id, item, name, description, student_no, phone, location, post_time, LF FROM " + DBHelper.ITEMS_TABLE + " WHERE LF = \"L\"";
        return DB.rawQuery(selectLostQuery, null);
    }

    //selecting all records with "F" in the LF field
    public Cursor getFoundItems() {
        String selectLostQuery = "SELECT item_id, item, name, description, student_no, phone, location, post_time, LF FROM " + DBHelper.ITEMS_TABLE + " WHERE LF = \"F\"";
        return DB.rawQuery(selectLostQuery, null);
    }

    //updating an item
    public boolean updateItem(String item_id, String name, String studentNo, String item, String description, String phone, String location) {
        ContentValues contentValuesUpdate = new ContentValues();
        contentValuesUpdate.put(DBHelper.NAME, name);
        contentValuesUpdate.put(DBHelper.STUDENT_NO, studentNo);
        contentValuesUpdate.put(DBHelper.ITEM, item);
        contentValuesUpdate.put(DBHelper.DESCRIPTION, description);
        contentValuesUpdate.put(DBHelper.PHONE, phone);
        contentValuesUpdate.put(DBHelper.LOCATION, location);
        long result = DB.update(DBHelper.ITEMS_TABLE, contentValuesUpdate, "item_id=?", new String[]{String.valueOf(item_id)});
        return result != -1;
    }

    //deleting item using item_id
    public boolean deleteItem(String item_id) {
        long result = DB.delete(DBHelper.ITEMS_TABLE, "item_id=?", new String[]{String.valueOf(item_id)});
        return result != -1;
    }

    //inserting a post
    public boolean insertPost(String name, String studentNo, String item, String description, String phone, String location, String postTIme, String LF){
        ContentValues contentValuesItems = new ContentValues();
        contentValuesItems.put(DBHelper.NAME, name);
        contentValuesItems.put(DBHelper.STUDENT_NO, studentNo);
        contentValuesItems.put(DBHelper.ITEM, item);
        contentValuesItems.put(DBHelper.DESCRIPTION, description);
        contentValuesItems.put(DBHelper.PHONE, phone);
        contentValuesItems.put(DBHelper.LOCATION, location);
        contentValuesItems.put(DBHelper.POST_TIME, postTIme);
        contentValuesItems.put(DBHelper.LF, LF);
        long result = DB.insert(DBHelper.ITEMS_TABLE, null, contentValuesItems);
        return result != -1;
    }

    //checking username and password matches the one in the Admin table
    public boolean checkUsernamePassword(String username, String password){
        Cursor cursor = DB.rawQuery("SELECT * FROM admins WHERE username = ? AND password = ?", new String[] {username,password});
        return cursor.getCount() > 0;
    }
}
