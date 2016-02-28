package me.recruit.recruitme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by AlexLand on 2016-02-27.
 */
public class DatabaseUtil extends SQLiteOpenHelper{
    private static String DATABASE_NAME = "DB";
    private static int DATABASE_VERSION = 1;

    public static final String TABLE_CANDIDATES = "candidates";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CANDIDATE_JSON = "candidatejson";

    private static final String INSERT = "INSERT INTO ";
    private static final String VALUES = " VALUES ";
    private static final String CANDIDATES_COLUMNS = "("
            + COLUMN_CANDIDATE_JSON + ")";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_CANDIDATES + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_CANDIDATE_JSON + " text"
            + ");";

    public DatabaseUtil(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(DATABASE_CREATE);

        }catch (SQLiteException e){
            e.printStackTrace();
        }
    }

    /**
     * Deletes all existing data from the table and re-creates the table
     * @param db the database to re-create
     * @param oldVersion the current version of the database
     * @param newVersion the version of the database to move to
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseUtil.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDIDATES);
        onCreate(db);
    }

    public void addCandidate(SQLiteDatabase db, String candidateJSON) {
        String statement = INSERT + TABLE_CANDIDATES + CANDIDATES_COLUMNS + VALUES + "("
                + "\"" + candidateJSON + "\")";
        db.execSQL(statement);
        Log.i("DatabaseUtil", statement);
    }
}
