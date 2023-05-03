package com.ranmal.ebusapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class AuthDatabase extends SQLiteOpenHelper {
    public AuthDatabase(@Nullable Context context) {
        super(context, "auth_db", null, 1);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseContract.SQL.CREATE_CACHE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DatabaseContract.SQL.DELETE_CACHE);
        onCreate(sqLiteDatabase);
    }
}
