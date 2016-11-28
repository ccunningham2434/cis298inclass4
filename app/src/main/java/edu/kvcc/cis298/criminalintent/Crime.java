package edu.kvcc.cis298.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by dbarnes on 10/5/2016.
 */
public class Crime {

    //Variables
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;

    //Constructor
    public Crime() {
        // >Call the constructor below and send over a randomly...
        // >...generated uuid since on was no sene to this constructor.
        this(UUID.randomUUID());
    }

    // >Create a new crime from a given uuid parameter.
    // >Primaraly used when creating from the DB.
    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
    }

    //Make a 4 parameter constructor to restore a crime from stored data
    public Crime(UUID uuid, String title, Date date, boolean isSolved) {
        mId = uuid;
        mTitle = title;
        mDate = date;
        mSolved = isSolved;
    }

    //Getters and Setters
    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }
}
