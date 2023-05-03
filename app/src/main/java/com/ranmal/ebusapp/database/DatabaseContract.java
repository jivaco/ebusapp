package com.ranmal.ebusapp.database;

import android.provider.BaseColumns;

public final class DatabaseContract {
    private DatabaseContract() {
    }

    public static final class Metadata implements BaseColumns {
        public static final String TABLE_NAME = "auth_tbl";
        public static final String COL_EMAIL = "email";
        public static final String COL_PW = "password";
        public static final String[] COLS = {COL_EMAIL, COL_PW};
    }

    public static final class SQL {
        public static final String CREATE_CACHE = "CREATE TABLE " + Metadata.TABLE_NAME + " (" +
                Metadata.COL_EMAIL + " TEXT UNIQUE," +
                Metadata.COL_PW + " TEXT" +
                ")";
        public static final String DELETE_CACHE = "DROP TABLE IF EXISTS " + Metadata.TABLE_NAME;
        public static final String GET_ALL_USERS = "SELECT * FROM " + Metadata.TABLE_NAME;
        public static final String SORT_ASC = Metadata.COL_EMAIL + " ASC";
        public static final String SORT_DESC = Metadata.COL_EMAIL + " DESC";
    }
}
