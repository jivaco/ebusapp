package com.ranmal.ebusapp.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranmal.ebusapp.database.DatabaseContract;
import com.ranmal.ebusapp.database.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImplementation implements UserRepository {

    private final SQLiteDatabase db;

    public UserRepositoryImplementation(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void insert(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Metadata.COL_EMAIL, user.email);
        contentValues.put(DatabaseContract.Metadata.COL_PW, user.password);
        db.insert(DatabaseContract.Metadata.TABLE_NAME, null, contentValues);
    }

    @Override
    public List<User> getAll() {
        Cursor result = db.query(DatabaseContract.Metadata.TABLE_NAME, DatabaseContract.Metadata.COLS, null, null, null, null, DatabaseContract.SQL.SORT_ASC);
        List<User> users = new ArrayList<>();
        int email_col_index = result.getColumnIndex(DatabaseContract.Metadata.COL_EMAIL);
        int pw_col_index = result.getColumnIndex(DatabaseContract.Metadata.COL_PW);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()){
            User user = new User();
            user.email = result.getString(email_col_index);
            user.password = result.getString(pw_col_index);
            users.add(user);
        }
        result.close();
        return users;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public void deleteAll() {
        db.delete(DatabaseContract.Metadata.TABLE_NAME, null, null);
    }
}
