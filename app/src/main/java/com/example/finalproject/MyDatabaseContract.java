package com.example.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;

import androidx.annotation.Nullable;

public final class MyDatabaseContract {

    public MyDatabaseContract(){}

    public static final String DB_NAME = "myDb.db";

    public static abstract class TableHistory {
        public static final String TABLE_Name = "history";
        public static final String COL_NAME_ID = "id";
        public static final String COL_NAME_SEARCH = "search";
        public static final String COL_NAME_MAP_URL = "url";
    }

    public static class MyDatabaseHelper extends SQLiteOpenHelper {


        public static final int DATABASE_VERSION = 6;
        public static final String DATABASE_NAME = MyDatabaseContract.DB_NAME;
        public static final String TEXT_TYPE = " TEXT";
        public static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_HISTORY =
                "CREATE TABLE " + MyDatabaseContract.TableHistory.TABLE_Name + " ("+
                        MyDatabaseContract.TableHistory.COL_NAME_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MyDatabaseContract.TableHistory.COL_NAME_SEARCH +TEXT_TYPE+COMMA_SEP+
                        MyDatabaseContract.TableHistory.COL_NAME_MAP_URL +TEXT_TYPE+")";





        public static final String SQL_DELETE_HISTORY = "DROP TABLE IF EXISTS " +MyDatabaseContract.TableHistory.TABLE_Name;

        public MyDatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }



        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_HISTORY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_HISTORY);
            onCreate(db);
        }

        Cursor readAllData(){
            String query = "SELECT * FROM " + MyDatabaseContract.TableHistory.TABLE_Name;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            if (db != null){
                cursor = db.rawQuery(query,null);
            }
            return cursor;

        }

        public void deleteEntry(String row) {

            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TableHistory.TABLE_Name, TableHistory.COL_NAME_ID + " = ?",
                    new String[] { String.valueOf(row) });
            db.close();

        }

    }

}
