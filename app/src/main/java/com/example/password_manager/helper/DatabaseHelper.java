package com.example.password_manager.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME = "COUNTRIES";

    // Table columns
    public static final String _ID = "_id";
        public static final String PLATFORM = "platform";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String CREATED_AT = "created_at";
        public static final String UPDATED_AT = "updated_at";

    // Database Information
    static final String DB_NAME = "PasswordManager.db";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PLATFORM + " TEXT NOT NULL," + EMAIL + " TEXT NOT NULL, " + PASSWORD + " TEXT, " + CREATED_AT + " TEXT, " + UPDATED_AT + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
