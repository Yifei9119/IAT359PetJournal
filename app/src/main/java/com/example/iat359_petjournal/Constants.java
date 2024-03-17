package com.example.iat359_petjournal;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;

public class Constants {
    public static final String DATABASE_NAME = "petdatabase";
    public static final String TABLE1_NAME = "PETTABLE";

    public static final String TABLE2_NAME = "SCHEDULETABLE";
    public static final String PETID = "_id";
    public static final String NAME = "Name";
    public static final String TYPE = "Type";

    public static final String TASKID = "_id";
    public static final String TIME = "_id";
    public static final String DESCRIPTION = "_id";
    public static final String TABLE3_NAME = "PHOTOTABLE";
    public static final String IMAGEID = "_id";


    public static final int DATABASE_VERSION = 4;
}