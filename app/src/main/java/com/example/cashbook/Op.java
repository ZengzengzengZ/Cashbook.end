package com.example.cashbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Op {
    private Helper helper = null;
    public Op(Context context){
        helper = new Helper(context);
    }
    public void add(String Title,String Date,String Money,String Type) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Title",Title);
        values.put("Date",Date);
        values.put("Money",Money);
        values.put("Type",Type);
        long id = db.insert("information",null,values);
        db.close();
    }
    public int delete(long id){
        SQLiteDatabase db = helper.getWritableDatabase();
        int number = db.delete("information", "_id=?", new String[]{id+""});
        db.close();
        return number;
    }
    public void updata(long id,String Title,String Date,String Money,String Type)
    {
        ContentValues values = new ContentValues();
        values.put("Title",Title);
        values.put("Date",Date);
        values.put("Money",Money);
        values.put("Type",Type);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update("information",values, "_id=?", new String[]{id+""});
        db.close();
    }
    public Bills find(int id){
        Bills bill=new Bills();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("information", null, "_id=?", new String[]{id+""},null, null, null);
        if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                bill.setCostTitle(cursor.getString(cursor.getColumnIndex("Title")));
                bill.setCostDate(cursor.getString(cursor.getColumnIndex("Date")));
                bill.setCostMoney(cursor.getString(cursor.getColumnIndex("Money")));
                bill.setCostType(cursor.getString(cursor.getColumnIndex("Type")));
            }
        }
        cursor.close();
        db.close();
        return bill;
    }
    public List<Bills> findbytitle(String title)
    {
        List<Bills> bills = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from information where Title like '%"+title +"%'",null);
        if(cursor.getCount()!=0)
        {
            while (cursor.moveToNext()){
                Bills bill=new Bills();
                bill.setCostTitle(cursor.getString(cursor.getColumnIndex("Title")));
                bill.setCostDate(cursor.getString(cursor.getColumnIndex("Date")));
                bill.setCostMoney(cursor.getString(cursor.getColumnIndex("Money")));
                bill.setCostType(cursor.getString(cursor.getColumnIndex("Type")));
                bill.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                bills.add(bill);
            }
        }
        return bills;
    }
    public boolean CanConvert(String str)
    {
        try{
            double tem = Double.parseDouble(str);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }
    public List<Bills> findbytype(String Type)
    {
        List<Bills> bills = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from information where Type like '%"+Type +"%'",null);
        if(cursor.getCount()!=0)
        {
            while (cursor.moveToNext()){
                Bills bill=new Bills();
                bill.setCostTitle(cursor.getString(cursor.getColumnIndex("Title")));
                bill.setCostDate(cursor.getString(cursor.getColumnIndex("Date")));
                bill.setCostMoney(cursor.getString(cursor.getColumnIndex("Money")));
                bill.setCostType(cursor.getString(cursor.getColumnIndex("Type")));
                bill.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                bills.add(bill);
            }
        }
        return bills;
    }    public List<Bills> findbydate(String Date)
    {
        List<Bills> bills = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from information where Date like '%"+Date+"%'",null);
        if(cursor.getCount()!=0)
        {
            while (cursor.moveToNext()){
                Bills bill=new Bills();
                bill.setCostTitle(cursor.getString(cursor.getColumnIndex("Title")));
                bill.setCostDate(cursor.getString(cursor.getColumnIndex("Date")));
                bill.setCostMoney(cursor.getString(cursor.getColumnIndex("Money")));
                bill.setCostType(cursor.getString(cursor.getColumnIndex("Type")));
                bill.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                bills.add(bill);
            }
        }
        return bills;
    }

    public long data_count()
    {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from information",null);
        cursor.moveToFirst();
        long cnt = cursor.getLong(0);
        cursor.close();
        return cnt;
    }


}