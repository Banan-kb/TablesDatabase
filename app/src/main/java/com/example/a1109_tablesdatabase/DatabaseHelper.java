package com.example.a1109_tablesdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Mitch on 2016-05-13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users.db";
    public static final String TABLE_NAME = "users_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "PRODUCT_NAME";
    public static final String COL3 = "PRODUCT_QUANTITY";
    //public static final String COL4 = "Unused";

    /* Constructor */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /* Code runs automatically when the dB is created */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " PRODUCT_NAME TEXT, PRODUCT_QUANTITY TEXT)";
        db.execSQL(createTable);
    }

    /* Every time the dB is updated (or upgraded) */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /* Basic function to add data. REMEMBER: The fields
       here, must be in accordance with those in
       the onCreate method above.
    */
    public boolean addData(String productName, String productQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, productName);
        contentValues.put(COL3, productQuantity);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data are inserted incorrectly, it will return -1
        if(result == -1) {return false;} else {return true;}
    }

    public Integer deleteData (String id){
        SQLiteDatabase db= this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});

    }

    /* Returns only one result */
    public Cursor structuredQuery(int ID) {
        SQLiteDatabase db = this.getReadableDatabase(); // No need to write
        Cursor cursor = db.query(TABLE_NAME, new String[]{COL1,
                        COL2, COL3}, COL1 + "=?",
                new String[]{String.valueOf(ID)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
    
    public Cursor getSpecificResult(String pName){
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor data = db.query(TABLE_NAME, new String[]{COL1,
                COL2, COL3}, COL2+"=?", new String[]{pName}, null, null, null, null);

        if (data != null)
            data.moveToFirst();

        return data;
    }

    // Return everything inside the dB
    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
}