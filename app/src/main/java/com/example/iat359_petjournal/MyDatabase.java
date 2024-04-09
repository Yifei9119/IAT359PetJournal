package com.example.iat359_petjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Blob;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MyDatabase {
    private SQLiteDatabase db;
    private Context context;
    private final MyHelper helper;

    public MyDatabase(Context c){
        context = c;
        helper = new MyHelper(context);
    }

//    inserts/adds new pet data
    public long insertPetData (String name, String type, String gender)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);
        contentValues.put(Constants.TYPE, type);
        contentValues.put(Constants.GENDER, gender);

        long id = db.insert(Constants.TABLE1_NAME, null, contentValues);
        Log.d("mylog", "" + id);
        return id;
    }

    public long insertEventData (String name, String date, String startTime, String endTime, String petName)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TASK, name);
        contentValues.put(Constants.DATE, date);
        contentValues.put(Constants.START_TIME, startTime);
        contentValues.put(Constants.END_TIME, endTime);
        contentValues.put(Constants.PET_NAME, petName);

        long id = db.insert(Constants.TABLE2_NAME, null, contentValues);
        Log.d("mylog", "" + id);
        return id;
    }

//    updates the pet names in db
    public long updatePetNameData (String name, String ids)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);

        long id = db.update(Constants.TABLE1_NAME, contentValues, "_id=?", new String[]{ids});
        return id;
    }

    // below is the method for deleting pets.
    public void deletePet(String id) {

        // on below line we are creating
        // a variable to write our database.
        db = helper.getWritableDatabase();

        // on below line we are calling a method to delete our
        // pet and we are comparing it with our pet id.
        db.delete(Constants.TABLE1_NAME, "" +
                "_id=?", new String[]{id});
    }

    // below is the method for deleting photos.
    public void deletePhoto(String id) {

        // on below line we are creating
        // a variable to write our database.
        db = helper.getWritableDatabase();

        // on below line we are calling a method to delete our
        // pet and we are comparing it with our pet id.
        db.delete("Photos", "" +
                "Size=?", new String[]{id});
    }

    public void deleteEvent(String id) {

        // on below line we are creating
        // a variable to write our database.
        db = helper.getWritableDatabase();

        // on below line we are calling a method to delete our
        // pet and we are comparing it with our pet id.
        db.delete(Constants.TABLE2_NAME, "" +
                "_id=?", new String[]{id});
    }

//inserts photos to the photo table
    public long insertPhotoData (int photoNum, byte[] bmap, String sizeText){
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", photoNum);
        contentValues.put("Photo", bmap);
        contentValues.put("Size", sizeText);
       long id = db.insert("Photos", null, contentValues);
        return id;
    }

    //gets all the data from photo table
    public Cursor getPhotoData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Photos.ID, Photos.Photo, Photos.Size FROM Photos", null);

        return cursor;
    }

//gets all the data from pet table
    public Cursor getPetData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {Constants.PETID, Constants.NAME, Constants.TYPE};
        Cursor cursor = db.query(Constants.TABLE1_NAME, columns, null, null, null, null, null);
        return cursor;
    }

    //gets all the data from task table for schedule
    public Cursor getEventData(String petName)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");
        String dateString = dateFormat.format(currentDate);
        String selection = Constants.PET_NAME + "='" + petName + "'";

        String[] columns = {Constants.TASKID, Constants.TASK, Constants.DATE, Constants.START_TIME, Constants.END_TIME, Constants.PET_NAME};
        Cursor cursor = db.query(Constants.TABLE2_NAME, columns, selection + " AND Date = ?" , new String[]{dateString}, null, null, Constants.START_TIME);
        Log.d("mylog", "getEventData: ");
        return cursor;
    }


//gets the selected pet from the database
    public String getSelectedData(String type)
    {
        //select pet from database of id type
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.NAME, Constants.TYPE, Constants.GENDER};

        String selection = Constants.PETID + "='" +type+ "'";  //Constants.PETID = '_id'
        Cursor cursor = db.query(Constants.TABLE1_NAME, columns, selection, null, null, null, null);

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {

            int index1 = cursor.getColumnIndex(Constants.NAME);
            int index2 = cursor.getColumnIndex(Constants.TYPE);
            int index3 = cursor.getColumnIndex(Constants.GENDER);

            String petName = cursor.getString(index1);
            String petBreed = cursor.getString(index2);
            String petGender = cursor.getString(index3);

            buffer.append(petName + "," + petBreed + "," + petGender +"\n");
        }
        return buffer.toString();
    }

}

