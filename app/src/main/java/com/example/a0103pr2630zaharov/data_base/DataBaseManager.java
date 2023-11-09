package com.example.a0103pr2630zaharov.data_base;

import static com.example.a0103pr2630zaharov.data_base.DataBaseConst.RESULTS_EXPRESSIONS;
import static com.example.a0103pr2630zaharov.data_base.DataBaseConst.RESULTS_ID;
import static com.example.a0103pr2630zaharov.data_base.DataBaseConst.RESULTS_RES;
import static com.example.a0103pr2630zaharov.data_base.DataBaseConst.TABLE_NAME_RESULTS;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a0103pr2630zaharov.data.Results;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {
    private Context context;
    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        this.context = context;
        dbHelper = new DataBaseHelper(this.context);
    }

    public void openDB() {
        db = dbHelper.getWritableDatabase();
    }

    public void closeDB() {
        db.close();
    }
    @SuppressLint("Range")
    public List<Results> getResults() {
        List<Results> results = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_RESULTS, null);
        for (; cursor.moveToNext(); ) {
            Results result = new Results();
            result.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(RESULTS_ID))));
            result.setExpression(cursor.getString(cursor.getColumnIndex(RESULTS_EXPRESSIONS)));
            result.setRes(cursor.getString(cursor.getColumnIndex(RESULTS_RES)));
            results.add(result);
        }
        cursor.close();
        return results;
    }
    public void addResult(Results result){
        ContentValues cv = new ContentValues();
        cv.put(RESULTS_EXPRESSIONS, result.getExpression());
        cv.put(RESULTS_RES, result.getRes());
        db.insert(TABLE_NAME_RESULTS, null, cv);
    }
    public void deleteResult(Results result){
        db.delete(TABLE_NAME_RESULTS, RESULTS_ID + " = " + result.getId(), null);
    }
}
