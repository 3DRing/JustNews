package com.ringov.justnews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Сергей on 31.12.2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "just_news_database";

    private static class HashTable {
        private static final String NAME = "hashes";

        private static final String CREATE = "CREATE TABLE " + HashTable.NAME + "("
                + Columns.HASH_STRING + " INTEGER PRIMARY KEY" + ")";

        private static final String DROP = "DROP TABLE IF EXISTS " + NAME;

        public class Columns{
            private static final String HASH_STRING = "hash_string";
        }

    }

    public DBHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(HashTable.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(HashTable.CREATE);
        onCreate(sqLiteDatabase);
    }

    public void addHash(long hash){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HashTable.Columns.HASH_STRING, hash);

        db.insert(HashTable.NAME, null, values);
        db.close();
    }

    public boolean containsHash(long hash){
        String selectQuery =
                "SELECT  * FROM " + HashTable.NAME +
                " WHERE " + HashTable.Columns.HASH_STRING + " = " + String.valueOf(hash);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int count = cursor.getCount();

        db.close();

        return count == 0 ? false : true;
    }

    public void clearHashTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "  + HashTable.NAME);
        db.close();
    }
}