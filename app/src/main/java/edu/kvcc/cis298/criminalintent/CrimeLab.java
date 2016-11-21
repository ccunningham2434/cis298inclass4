package edu.kvcc.cis298.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.media.session.PlaybackState;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

import edu.kvcc.cis298.criminalintent.database.CrimeBaseHelper;
import edu.kvcc.cis298.criminalintent.database.CrimeCursorWrapper;
import edu.kvcc.cis298.criminalintent.database.CrimeDbSchema;

import static edu.kvcc.cis298.criminalintent.database.CrimeDbSchema.*;

/**
 * Created by dbarnes on 10/19/2016.
 */
public class CrimeLab {
    //Static variable to hold the instance of the CrimeLab
    private static CrimeLab sCrimeLab;

    //Class level variable to hold the sqlite database.
    private SQLiteDatabase mDatabase;
    //This context will be the hosting activity. It will get
    //assigned in the constructor.
    private Context mContext;

    //This is a static get method to get the single instance of the class.
    public static CrimeLab get(Context context) {
        //If we dont' have an instace, we create a new one.
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        //Regardless of whether we created it, or
        //it already existed, we need to return it
        return sCrimeLab;
    }

    //This is the constructor. It is private.
    //It can't be used from outside classes.
    private CrimeLab(Context context) {
        //Set the class level context to the one passed in.
        mContext = context.getApplicationContext();
        //Set the class level database
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addCrime(Crime c) {
        // >This will call the geContentValues method defined below and...
        // >...retrieve the key value for each property of the crime.
        ContentValues values = getContentValues(c);

        // >Insert a new record into the database using the values.
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();

        // >Define a crimeCursorWrapper to be used when reading the DB.
        // >null for where clause and arguments, to select all records.
        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            // >While the cursor is not after the last record in teh result set of executing the query.
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return crimes;
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(
                // >where clause
                CrimeTable.Cols.UUID + " = ?"
                // >args for the clause
                , new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        // >Get values from the crime.
        ContentValues values = getContentValues(crime);
        // >Update the table.
        // >takes in the table name, update values, and the where clause to determine which
        // >record to update.
        mDatabase.update(CrimeTable.NAME, values
                // >This makes the where clause paramaterized to prevent sql injection.
                , CrimeTable.Cols.UUID + " = ?",
                new String[] { uuidString});
    }

    private static ContentValues getContentValues(Crime crime) {
        // >Make a content values object that will store key => value.
        // >As far as teh DB is concerned this will be the table column we want to...
        // >...insert to, and then the value to insert. This means that we need a...
        // >...statement for every column in the database, and an associated value.
        ContentValues values = new ContentValues();
        // >Put in the table column name, and the associated crime fields value.
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1: 0);

        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME
                , null // >Columns - null selects all columns.
                , whereClause
                , whereArgs
                , null // >groupBy
                , null // >having
                , null // >orderBy
        );
        return new CrimeCursorWrapper(cursor);
    }



}
