package com.example.iat359_petjournal;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;

public class Constants {
//    constants
    public static final String DATABASE_NAME = "petdatabase";
    public static final String TABLE1_NAME = "PETTABLE";

    public static final String TABLE2_NAME = "SCHEDULETABLE";
    public static final String PETID = "_id";
    public static final String NAME = "Name";
    public static final String TYPE = "Type";

    public static final String TASKID = "_id";
    public static final String TASK = "Task";
    public static final String DATE = "Date";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String PET_NAME = "petName";


    public static final int DATABASE_VERSION = 29;
    public static final String GENDER = "gender";
}