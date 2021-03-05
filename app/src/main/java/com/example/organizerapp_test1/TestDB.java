package com.example.organizerapp_test1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.util.Log;
import android.view.View;

public class TestDB {
    static final String KEY_ROWID = "_id";
    static final String KEY_TYPE = "type";
    static final String KEY_ITEM = "item";
    static final String TAG = "TestDBAdapter";
    static final String DATABASE_NAME = "FridgeItems";
    static final String DATABASE_TABLE = "ProduceItems";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE = "create table ProduceItems (_id integer primary key autoincrement, " + "type text not null, item text not null);";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public TestDB (Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);

    }//

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            }catch (SQLException e)
            {
                e.printStackTrace();
            }//trycatch
        }//onCreate

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }// DatabaseHelper

    public TestDB open() throws SQLException //----this opens the database---
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() //--closes database--
    {
        DBHelper.close();
    }

    //--insert contact
    public long insertType(String type, String item)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TYPE, type);
        initialValues.put(KEY_ITEM, item);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public void deleteItem(String rowItem, String type)
    {
       // return db.delete(DATABASE_TABLE, KEY_ITEM + "=" + rowItem, null) > 0;
        db.execSQL("DELETE FROM " + DATABASE_TABLE + " WHERE " + KEY_TYPE + "=" + "'" + type + "'" + " AND " + KEY_ITEM + "=" + "'" + rowItem + "'");
    }

    public void deleteRoom(String getType)
    {
        db.execSQL("DELETE FROM " + DATABASE_TABLE + " WHERE " + KEY_TYPE + "=" + "'" + getType + "'");
    }

    public void removeAll()
    {
        db.execSQL("DELETE FROM " + DATABASE_TABLE);
        // return CLEAR_MESSAGE;
    }

    public Cursor getItems()
    {

        return db.query(DATABASE_TABLE, new String[] {KEY_ITEM}, null, null, null, null, null);

    }

    public Cursor getAllInfo()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TYPE, KEY_ITEM}, null, null, null, null, null);
    }

    public Cursor getTypeOnly()
    {

        return db.query(true, DATABASE_TABLE, new String[] {KEY_TYPE}, null, null, null, null, null, null);
    }



    public Cursor getItemExist(String type) throws SQLException
    {
        Cursor mCursor = db.query(DATABASE_TABLE, new String[]{KEY_ITEM}, KEY_TYPE + "=" + "'" + type + "'", null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }//
        return mCursor;
    }




    ////retrieves particular contacts
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TYPE, KEY_ITEM},
                KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }//getcontact


    /*
    //---updates contacts---
    public boolean updateContact(long rowId, String room, String item, String container)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_ROOM, room);
        args.put(KEY_ITEM, item);
        args.put(KEY_CONTAINER, container);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

     */

    //---updates item---
    public boolean updateItem(long rowId, String type, String item)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_TYPE, type);
        args.put(KEY_ITEM, item);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}//end
