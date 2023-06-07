package com.ranmal.ebusapp.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranmal.ebusapp.database.DatabaseContract;
import com.ranmal.ebusapp.schemas.User;

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
    public User getCurrent() {
        Cursor result = db.query(DatabaseContract.Metadata.TABLE_NAME, DatabaseContract.Metadata.COLS, null, null, null, null, DatabaseContract.SQL.SORT_ASC);
        int email_col_index = result.getColumnIndex(DatabaseContract.Metadata.COL_EMAIL);
        int pw_col_index = result.getColumnIndex(DatabaseContract.Metadata.COL_PW);
        User user = null;
        if (result.moveToFirst()) {
            String email = result.getString(email_col_index);
            String password = result.getString(pw_col_index);
            user = new User(email, password);
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        Cursor result = db.query(
                DatabaseContract.Metadata.TABLE_NAME,
                DatabaseContract.Metadata.COLS,
                DatabaseContract.Metadata.COL_EMAIL + "=?",
                new String[] {email},
                null,
                null,
                DatabaseContract.SQL.SORT_ASC
        );
        int pw_col_index = result.getColumnIndex(DatabaseContract.Metadata.COL_PW);
        User user = null;
        if (result.moveToFirst()) {
            String password = result.getString(pw_col_index);
            user = new User(email, password);
        }
        return user;
    }

    @Override
    public void deleteAll() {
        db.delete(DatabaseContract.Metadata.TABLE_NAME, null, null);
    }
}
