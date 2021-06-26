package com.example.cashbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {
    public Helper(Context context)
    {
        super(context,"cost.db",null,1);
    }
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE information(_id INTEGER PRIMARY KEY AUTOINCREMENT,Title VARCHAR(20),Date,Money VARCHAR(20),Type VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}

