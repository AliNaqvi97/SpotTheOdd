package com.ahn.spottheodd;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ali on 2/19/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
// Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "kuku_kube.db";

    // Table name
    private static final String TABLE_ARCADE = "arcade_score";
    private static final String TABLE_CLASSIC = "classic_score";
    private static final String TABLE_HARD = "hard_score";

    // Score Table Columns names
    private static final String KEY_ID_SCORE = "_id";
    private static final String KEY_SCORE = "score_value";

    // Strings to create table
    private static final String CREATE_ARCADE_TABLE = "CREATE TABLE " + TABLE_ARCADE + "("
            + KEY_ID_SCORE + " INTEGER PRIMARY KEY,"
            + KEY_SCORE + " TEXT" + ")";
    private static final String CREATE_CLASSIC_TABLE = "CREATE TABLE " + TABLE_CLASSIC + "("
            + KEY_ID_SCORE + " INTEGER PRIMARY KEY,"
            + KEY_SCORE + " TEXT" + ")";
    private static final String CREATE_HARD_TABLE = "CREATE TABLE " + TABLE_HARD + "("
            + KEY_ID_SCORE + " INTEGER PRIMARY KEY,"
            + KEY_SCORE + " TEXT" + ")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLASSIC_TABLE);
        db.execSQL(CREATE_HARD_TABLE);
        db.execSQL(CREATE_ARCADE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSIC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARCADE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HARD);

        // Create tables again
        onCreate(db);
    }

    // Adding new score
    public void addScoreClassic(int score) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_SCORE, score); // score value
        // Inserting Values
        db.insert(TABLE_CLASSIC, null, values);
    }
    public void addScoreArcade(int score) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_SCORE, score); // score value

        // Inserting Values
        db.insert(TABLE_ARCADE, null, values);
    }
    public void addScoreHard(int score) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_SCORE, score); // score value

        // Inserting Values
        db.insert(TABLE_HARD, null, values);
    }

    // Getting All Scores
    public int getAllScoresClassic() {


        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLASSIC + " ORDER BY " + KEY_SCORE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        ArrayList<Integer> data = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                data.add(cursor.getInt(1));
            } while (cursor.moveToNext());
        }

        if(!cursor.isClosed())
        {cursor.close();
        }
        // return score array

        Collections.sort(data, Collections.reverseOrder());
        return data.get(0);
    }

    public int getAllScoresArcade() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ARCADE + " ORDER BY " + KEY_SCORE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        ArrayList<Integer> data = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                data.add(cursor.getInt(1));
            } while (cursor.moveToNext());
        }

        if(!cursor.isClosed())
        {cursor.close();
        }
        // return score array

        Collections.sort(data, Collections.reverseOrder());
        return data.get(0);
    }

    public int getAllScoresHard() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HARD + " ORDER BY " + KEY_SCORE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        ArrayList<Integer> data = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                data.add(cursor.getInt(1));
            } while (cursor.moveToNext());
        }

        if(!cursor.isClosed())
        {cursor.close();
        }
        // return score array

        Collections.sort(data, Collections.reverseOrder());
        return data.get(0);
    }
}