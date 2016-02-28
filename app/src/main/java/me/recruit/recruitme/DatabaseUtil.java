package me.recruit.recruitme;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by AlexLand on 2016-02-27.
 */
public class DatabaseUtil extends SQLiteOpenHelper{
    private static String DATABASE_NAME = "DB";
    private static int DATABASE_VERSION = 1;

    public static final String TABLE_CANDIDATES = "candidates";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CANDIDATE_JSON = "candidatejson";

    private static final String INSERT_CANDIDATE = "INSERT INTO "
            + TABLE_CANDIDATES + " ("
            + COLUMN_CANDIDATE_JSON + ")"
            + " VALUES ";
//
//    private static final String INSERT = "INSERT INTO ";
//    private static final String VALUES = " VALUES ";
//    private static final String CANDIDATES_COLUMNS = "("
//            + COLUMN_CANDIDATE_JSON + ")";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_CANDIDATES + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_CANDIDATE_JSON + " text"
            + ");";

    private static final String UPDATE_CANDIDATE_PREFIX = "UPDATE "
            + TABLE_CANDIDATES + " SET "
            + COLUMN_CANDIDATE_JSON + " = ";

    private static final String UPDATE_CANDIDATE_SUFFIX = " WHERE "
            + COLUMN_ID + " = ";

    private static final String GET_ALL_CANDIDATES = "SELECT * FROM " + TABLE_CANDIDATES;

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
        String statement = INSERT_CANDIDATE + "("
                + "\'" + candidateJSON + "\')";
        db.execSQL(statement);
        Log.i("DatabaseUtil", statement);
    }

    public List[] getCandidatesList(SQLiteDatabase db) {

		String candidateJSONString = "";
		List<String> candidateList = new ArrayList<>();
		List<String> candidateListId = new ArrayList<>();

		try {
			candidateJSONString = HTTPUrlConnection.sendGet();
			candidateList = JSONParser.parseCandidateList(candidateJSONString);
			candidateListId = JSONParser.parseCandidateIdList(candidateJSONString);
		} catch (Exception e) {
			e.printStackTrace();
		}



//        List<String> candidateList = new ArrayList<>();
//        List<String> candidateListIds = new ArrayList<>();
//        String statement = GET_ALL_CANDIDATES;
//        Cursor cursor = db.rawQuery(statement, null);
//        if (cursor.moveToFirst()) {
//            while (cursor.isAfterLast() == false) {
//                String candidateJSON = cursor.getString(cursor.getColumnIndex(COLUMN_CANDIDATE_JSON));
//                candidateList.add(candidateJSON);
//                String candidateId = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
//                candidateListIds.add(candidateId);
//                cursor.moveToNext();
//            }
//        }
        return new List[] {candidateList, candidateListId};
    }

    public void updateCandidate(SQLiteDatabase db, String candidateId, String candidateJSON) {
        String statement = UPDATE_CANDIDATE_PREFIX + "\'" + candidateJSON + "\'" + UPDATE_CANDIDATE_SUFFIX + candidateId;
        db.execSQL(statement);
    }

    public void deleteDb(SQLiteDatabase db, Context context) {
        db.close();
        context.deleteDatabase(DATABASE_NAME);
    }
}
