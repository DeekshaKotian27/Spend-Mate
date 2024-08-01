package com.example.deeksha.spendmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class signupdb extends SQLiteOpenHelper {

    //initialzing
    private static final String table1="signup";
    private static final String table2="income";
    private static final String table3="expense";

    public signupdb(Context context)
    {
        super(context, "expense.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String t1="create Table "+table1+"(cust_id INTEGER primary key AUTOINCREMENT,name TEXT , mail TEXT unique,contact TEXT,password TEXT )";

        String t2="create Table "+table2+"(tr_id INTEGER primary key AUTOINCREMENT,cust_id integer,income_amt TEXT ,income_type TEXT ,income_desc TEXT,tr_date TEXT,foreign key(cust_id) references "+table1+"(cust_id))";

        String t3="create Table "+table3+"(tr_id INTEGER primary key AUTOINCREMENT,cust_id integer,exp_amt TEXT ,exp_type TEXT ,exp_desc TEXT,etr_date TEXT,foreign key(cust_id) references "+table1+"(cust_id))";


        //signup table
        db.execSQL(t1);

        //income table
        db.execSQL(t2);

        //expense table
        db.execSQL(t3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists "+table1);
        db.execSQL("drop Table if exists "+table2);
        db.execSQL("drop Table if exists "+table3);
        onCreate(db);
    }

    //insert values into the signup table
    public Boolean signupinsert(String name, String mail, String pass,String phone)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("mail", mail);
        contentValues.put("contact", phone);
        contentValues.put("password", pass);
        long result=DB.insert(table1, null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    //insert values into income table
    public Boolean incomeinsert(Integer id,String amount, String type, String desc,String date)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cust_id", id);
        contentValues.put("income_amt", amount);
        contentValues.put("income_type", type);
        contentValues.put("income_desc", desc);
        contentValues.put("tr_date", date);
        long result=DB.insert(table2, null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    //insert values into expense table
    public Boolean expenseinsert(Integer id,String amount, String type, String desc,String date)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cust_id", id);
        contentValues.put("exp_amt", amount);
        contentValues.put("exp_type", type);
        contentValues.put("exp_desc", desc);
        contentValues.put("etr_date", date);
        long result=DB.insert(table3, null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    //geting data of signup table
    public Cursor signupgetdata ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select mail,password,name,cust_id from "+table1, null);
        return cursor;

    }

    //getting data of income table
    public Cursor incomegetdata (Integer id)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select income_amt,income_type,income_desc,tr_date,tr_id from "+table1+" s, "+table2+" i where s.cust_id=i.cust_id and i.cust_id="+id, null);
        return cursor;

    }

    //getting data of expense table
    public Cursor expensegetdata (Integer id)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select exp_amt,exp_type,exp_desc,etr_date,tr_id from "+table3+" i join "+table1+" s where i.cust_id=s.cust_id and i.cust_id="+id, null);
        return cursor;

    }

    //getting data of income to dashboard activity
    public Cursor idata(Integer cid,Integer tid)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select income_amt,income_type,income_desc,tr_date from "+table1+" s, "+table2+" i where s.cust_id=i.cust_id and i.cust_id="+cid+" and i.tr_id="+tid, null);
        return cursor;

    }

    //getting data of expense to dashboard activity
    public Cursor edata (Integer cid,Integer tid)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select exp_amt,exp_type,exp_desc,etr_date from "+table1+" s, "+table3+" i where s.cust_id=i.cust_id and i.cust_id="+cid+" and i.tr_id="+tid, null);
        return cursor;

    }

    //updating values into income table
    public void updateIncome(Integer cid, Integer tid,String amount, String type, String desc,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("income_amt", amount);
        contentValues.put("income_type", type);
        contentValues.put("income_desc", desc);
        contentValues.put("tr_date", date);
        db.update(table2,contentValues,"cust_id="+cid+" and tr_id="+tid,null);
    }

    //updating values into expense table
    public void updateExpense(Integer cid, Integer tid,String amount, String type, String desc,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("exp_amt", amount);
        contentValues.put("exp_type", type);
        contentValues.put("exp_desc", desc);
        contentValues.put("etr_date", date);
        db.update(table3,contentValues,"cust_id="+cid+" and tr_id="+tid,null);
    }

    //deleting value from income
    public void delincome(Integer tid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table2,"tr_id="+tid,null);
    }

    //deleting value from expense
    public void delexpense(Integer tid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table3,"tr_id="+tid,null);
    }
}

