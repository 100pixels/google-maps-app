package com.example.ivansandoval.googlemaps.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

import static com.example.ivansandoval.googlemaps.database.PublicTransitContract.*;



public class PublicTransitDbHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="publicTransit.db";
    public  final String LOG_TAG="PublicTransitDbHelper";
    private static PublicTransitDbHelper publicTransitDbHelper;

    private PublicTransitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static PublicTransitDbHelper getInstance(Context context){
        if(publicTransitDbHelper == null){
            publicTransitDbHelper = new PublicTransitDbHelper(context);
        }
        return publicTransitDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(LOG_TAG,"Table "+ CompanyEntry.TABLE_NAME+" has been created");
        try{
            createDatabaseSchema(db);
        }catch(SQLException ex){
            Log.e(LOG_TAG,ex.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDatabaseSchema(SQLiteDatabase db) throws SQLException {

        // Tabla agency
        db.execSQL("CREATE TABLE " + CompanyEntry.TABLE_NAME + " ("
                + CompanyEntry._ID + " TEXT NOT NULL,"
                + CompanyEntry.AGENCY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT   NOT NULL ,"
                + CompanyEntry.NAME + " VARCHAR(50) NOT NULL,"
                + CompanyEntry.CITY + " VARCHAR(30) NOT NULL);");

        Log.i(LOG_TAG,"Table "+ CompanyEntry.TABLE_NAME+" has been created");

        // Tabla route
        db.execSQL("CREATE TABLE " + RouteEntry.TABLE_NAME + " ("
                + RouteEntry._ID + " TEXT NOT NULL,"
                + RouteEntry.ROUTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                + RouteEntry.SHORT_NAME + " VARCHAR(15) NOT NULL,"
                + RouteEntry.LONG_NAME + " VARCHAR(130) NOT NULL,"
                + RouteEntry.COLOR+ " VARCHAR(7) NOT NULL,"
                + RouteEntry.AGENCY_ID+ " INTEGER NOT NULL)," +
                " FOREIGN KEY (agency_id) REFERENCES agency(agency_id);");

        Log.i(LOG_TAG,"Table "+ RouteEntry.TABLE_NAME+" has been created");

        // Tabla journey
        db.execSQL("CREATE TABLE " + JourneyEntry.TABLE_NAME + " ("
                + JourneyEntry._ID + " TEXT NOT NULL,"
                + JourneyEntry.JOURNEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT   NOT NULL ,"
                + JourneyEntry.DIRECTION + " VARCHAR(50) NOT NULL,"
                + JourneyEntry.TIME_INTERVAL + " INTEGER NOT NULL,"
                + JourneyEntry.ROUTE_ID+ " INTEGER NOT NULL,"
                + " FOREIGN KEY (route_id) REFERENCES route(route_id);");

        Log.i(LOG_TAG,"Table "+ JourneyEntry.TABLE_NAME+" has been created");

        // Tabla stop
        db.execSQL("CREATE TABLE " + StopEntry.TABLE_NAME + " ("
                + StopEntry._ID + " TEXT NOT NULL,"
                + StopEntry.STOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT   NOT NULL ,"
                + StopEntry.LATITUDE + " REAL NOT NULL ,"
                + StopEntry.LONGITUDE + " REAL NOT NULL ,"
                + StopEntry.DESCRIPTION+ " VARCHAR(50) ,"
                + StopEntry.JOURNEY_ID+ " INTEGER NOT NULL,"
                +" FOREIGN KEY (journey_id) REFERENCES journey(journey_id);");

        Log.i(LOG_TAG,"Table "+ StopEntry.TABLE_NAME+" has been created");
    }



}
