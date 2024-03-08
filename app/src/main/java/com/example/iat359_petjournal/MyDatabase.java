package com.example.iat359_petjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Blob;

public class MyDatabase {
    private SQLiteDatabase db;
    private Context context;
    private final MyHelper helper;

    public MyDatabase(Context c){
        context = c;
        helper = new MyHelper(context);
    }

    public long insertPetData (String name, String type)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);
        contentValues.put(Constants.TYPE, type);

        long id = db.insert(Constants.TABLE1_NAME, null, contentValues);
        return id;
    }
    
//incomplete
    public long insertPhotoData (Blob image){
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        long id = db.insert(Constants.TABLE3_NAME, null, contentValues);
        return id;
    }


    public Cursor getPetData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {Constants.PETID, Constants.NAME, Constants.TYPE};
        Cursor cursor = db.query(Constants.TABLE1_NAME, columns, null, null, null, null, null);
        return cursor;
    }

    public Cursor getTaskData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {Constants.PETID, Constants.NAME, Constants.TYPE};
        Cursor cursor = db.query(Constants.TABLE1_NAME, columns, null, null, null, null, null);
        return cursor;
    }


    public String getSelectedData(String type)
    {
        //select plants from database of type 'herb'
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.NAME, Constants.TYPE};

        String selection = Constants.TYPE + "='" +type+ "'";  //Constants.TYPE = 'type'
        Cursor cursor = db.query(Constants.TABLE1_NAME, columns, selection, null, null, null, null);

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {

            int index1 = cursor.getColumnIndex(Constants.NAME);
            int index2 = cursor.getColumnIndex(Constants.TYPE);

            String petName = cursor.getString(index1);
            String petBreed = cursor.getString(index2);

            buffer.append(petName + "," + petBreed + "\n");
        }
        return buffer.toString();
    }

}

