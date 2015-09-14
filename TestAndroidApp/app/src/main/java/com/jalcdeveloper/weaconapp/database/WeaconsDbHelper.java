package com.jalcdeveloper.weaconapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Carles on 14/09/15.
 */
public class WeaconsDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Weaconsapp.db";

    public WeaconsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WeaconsContract.SQL_CREATE_SENSORS);
        db.execSQL(WeaconsContract.SQL_CREATE_EVENTS);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addSensor(Sensor sensor) {
        ContentValues values = new ContentValues();
        values.put(WeaconsContract.Sensors.COLUMN_NAME_TITLE, sensor.get_nombre());
        values.put(WeaconsContract.Sensors.COLUMN_NAME_DESC, sensor.get_descripcion());
        values.put(WeaconsContract.Sensors.COLUMN_NAME_CHANNEL, sensor.get_canal());
        values.put(WeaconsContract.Sensors.COLUMN_NAME_TYPE, sensor.get_tipo());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(WeaconsContract.Sensors.TABLE_NAME, null, values);
        db.close();
    }

}