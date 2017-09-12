package com.example.vishnu.tabact;

/**
 * Created by Vishnu on 31-Aug-17.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    String DB_PATH = null;
    private static String DB_NAME = "vishnudatabasejokes";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 10);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1", DB_PATH);
    }


    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    public ArrayList<String> getCursor(String tableName){
        //Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        Cursor cursor= myDataBase.query(tableName, null, null, null, null, null, null);
        ArrayList<String> data =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int indexID = cursor.getColumnIndex("_id");
                int indexDATE = cursor.getColumnIndex("JOKES");
                int i = cursor.getInt(indexID);
                data.add(cursor.getString(indexDATE));

            } while (cursor.moveToNext());
            //Collections.shuffle(data);
        }

        return data;
    }


    public Cursor query1(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        String[] table_name=new String[] {"SANTA","CLASS_ROOM","SANTA","CLASS_ROOM","SANTA","CLASS_ROOM"};
        String colId="_id";

        return myDataBase.query(table_name[0], null, null, null, null, null, null);
    }
    public Cursor query2(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        return myDataBase.query("CLASS_ROOM", null, null, null, null, null, null);
    }
    public Cursor query3(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        return myDataBase.query("COUPLES", null, null, null, null, null, null);
    }
    public Cursor query4(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        return myDataBase.query("SANTA", null, null, null, null, null, null);
    }
    public Cursor query5(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        return myDataBase.query("CLASS_ROOM", null, null, null, null, null, null);
    }
    public Cursor query6(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        return myDataBase.query("COUPLES", null, null, null, null, null, null);
    }
}

