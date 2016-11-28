package edu.kvcc.cis298.criminalintent.database;

/**
 * Created by dbarnes on 11/9/2016.
 */
public class CrimeDbSchema {

    //This inner class will define a constant for the name
    //of the table that we will be creating
    public static final class CrimeTable {
        public static final String NAME = "crimes";

        //This inner class will define some constants for the name
        //of the columns in the table that we will be creating
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
        }
    }
}
