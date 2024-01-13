package com.example.password_manager.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DatabaseManager(Context c) {
        context = c;
    }

    public DatabaseManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String platform,String email, String password) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.PLATFORM, platform);
        contentValue.put(DatabaseHelper.EMAIL, email);
        contentValue.put(DatabaseHelper.PASSWORD, password);
        contentValue.put(DatabaseHelper.CREATED_AT, Utils.currentDateTime());
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.PLATFORM,DatabaseHelper.EMAIL, DatabaseHelper.PASSWORD, DatabaseHelper.CREATED_AT };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    @SuppressLint("Range")
    public JSONArray load(){
        JSONArray array = new JSONArray();
        try (Cursor res = fetch()) {
            while (res.moveToNext()) {
                JSONObject jsonResult = new JSONObject();
                jsonResult.put("_id", res.getString(res.getColumnIndex("_id")));
                jsonResult.put("platform", res.getString(res.getColumnIndex("platform")));
                jsonResult.put("email", res.getString(res.getColumnIndex("email")));
                jsonResult.put("password", res.getString(res.getColumnIndex("password")));
                jsonResult.put("created_at", res.getString(res.getColumnIndex("created_at")));
                array.put(jsonResult);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return array;
    }

    public int update(long _id,String platform,String email, String password) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.PLATFORM, platform);
        contentValue.put(DatabaseHelper.EMAIL, email);
        contentValue.put(DatabaseHelper.PASSWORD, password);
        contentValue.put(DatabaseHelper.UPDATED_AT, Utils.currentDateTime());
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValue, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }
}
