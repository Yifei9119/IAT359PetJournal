package com.example.iat359_petjournal;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyHelper extends SQLiteOpenHelper {

    private Context context;


    private static final String CREATE_TABLE1 =
            "CREATE TABLE "+
                    Constants.TABLE1_NAME + " (" +
                    Constants.PETID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.NAME + " TEXT, " +
                    Constants.TYPE + " TEXT, "+
                    Constants.GENDER+" TEXT);"
            ;
    private static final String CREATE_TABLE2 =
            "CREATE TABLE "+

                    Constants.TABLE2_NAME + " (" +
                    Constants.TASKID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.TASK + " TEXT, " +
                    Constants.DATE + " TEXT, " +
                    Constants.START_TIME + " TEXT, "+
                    Constants.END_TIME + " TEXT, "+
                    Constants.PET_NAME + " TEXT);"
            ;


    private static final String DROP_TABLE1 = "DROP TABLE IF EXISTS " + Constants.TABLE1_NAME;
    private static final String DROP_TABLE2 = "DROP TABLE IF EXISTS " + Constants.TABLE2_NAME;

    public MyHelper(Context context){
        super (context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE1);
            db.execSQL(CREATE_TABLE2);
            db.execSQL("CREATE TABLE Photos(ID INT,Photo Blob, Size Text);");

            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onCreate() db", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE1);
            db.execSQL(DROP_TABLE2);
            db.execSQL("DROP TABLE IF EXISTS Photos;");



            onCreate(db);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onUpgrade() db", Toast.LENGTH_LONG).show();
        }
    }
}

