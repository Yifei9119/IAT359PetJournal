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
    public long insertPetData (String name, String type)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);
        contentValues.put(Constants.TYPE, type);

        long id = db.insert(Constants.TABLE1_NAME, null, contentValues);
        Log.d("mylog", "" + id);
        return id;
    }

    public long insertEventData (String name, String date, String startTime, String endTime)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TASK, name);
        contentValues.put(Constants.DATE, date);
        contentValues.put(Constants.START_TIME, startTime);
        contentValues.put(Constants.END_TIME, endTime);

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

    // below is the method for deleting our course.
    public void deletePet(String id) {

        // on below line we are creating
        // a variable to write our database.
        db = helper.getWritableDatabase();

        // on below line we are calling a method to delete our
        // pet and we are comparing it with our pet id.
        db.delete(Constants.TABLE1_NAME, "" +
                "_id=?", new String[]{id});
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

//incomplete
    public long insertPhotoData (Blob image){
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        long id = db.insert(Constants.TABLE3_NAME, null, contentValues);
        return id;
    }

//gets all the data from pet table
    public Cursor getPetData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {Constants.PETID, Constants.NAME, Constants.TYPE};
        Cursor cursor = db.query(Constants.TABLE1_NAME, columns, null, null, null, null, null);
        return cursor;
    }

    public Cursor getEventData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");
        String dateString = dateFormat.format(currentDate);
        Log.d("mylog", "hii" + dateString);

        String[] columns = {Constants.TASKID, Constants.TASK, Constants.DATE, Constants.START_TIME, Constants.END_TIME};
        Cursor cursor = db.query(Constants.TABLE2_NAME, columns, "Date = ?", new String[]{dateString}, null, null, Constants.START_TIME);
        Log.d("mylog", "getEventData: ");
        return cursor;
    }

//    public Cursor getTaskData(String type)
//    {
//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        String[] columns = {Constants.TASKID, Constants.NAME, Constants.TYPE};
//        String selection = Constants.PETID + "='" +type+ "'";  //Constants.PETID = '_id'
//
//        Cursor cursor = db.query(Constants.TABLE1_NAME+" inner join "+Constants.TABLE2_NAME+" on PETTABLE._id = SCHEDULETABLE.PetID", columns, selection, null, null, null, null);
//        Cursor cursor = db.query(Constants.TABLE2_NAME, columns, selection, null, null, null, null);
//        return cursor;
//    }


//gets the selected pet from the database
    public String getSelectedData(String type)
    {
        //select pet from database of id type
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.NAME, Constants.TYPE};

        String selection = Constants.PETID + "='" +type+ "'";  //Constants.PETID = '_id'
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

    public String getSelectedIDData(String name)
    {
        //select pet from database of type 'id'
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.NAME, Constants.TYPE};

        String selection = Constants.NAME + "='" +name+ "'";  //Constants.TYPE = 'type'
        Cursor cursor = db.query(Constants.TABLE1_NAME, columns, selection, null, null, null, null);

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {

            int index1 = cursor.getColumnIndex(Constants.PETID);
            int index2 = cursor.getColumnIndex(Constants.NAME);


            String petID = cursor.getString(index1);
            String petName = cursor.getString(index2);

            buffer.append(petID+","+petName);
        }
        return buffer.toString();
    }


}

