package com.example.week7assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    private Context context; // Add a member variable to store the Context

    public class UserData {
        private String firstname;
        private String lastname;
        private String idnumber;
        private String major;
        private String gender;
        private String weight;
        private String date;}
    public DBHelper(Context context) {
        super(context, "hello8888888818888w.db", null, 2);
        this.context = context; // Store the Context
    }


    @Override
    public void onCreate(SQLiteDatabase DB) {
        // Create the student table
        DB.execSQL("create Table student(firstname TEXT, lastname TEXT, idnumber TEXT primary key, major TEXT,gender TEXT,weight TEXT, date TEXT )");
        // Create the units table
        DB.execSQL("create Table units(idnumber TEXT primary key, totalfees TEXT, feespaid TEXT, feesbalance TEXT, clearancedate TEXT)");

        DB.execSQL("create Table users(firstname TEXT, lastname TEXT, emailAddress TEXT primary key, password TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists student");
        DB.execSQL("drop Table if exists units");

    }

    // Methods for student table

    public Boolean insertuserdata(String firstname, String lastname, String idnumber, String major, String gender, String weight, String date){
        SQLiteDatabase DB= this.getWritableDatabase();

        String query = "SELECT MAX(idnumber) FROM student";
        Cursor cursor = DB.rawQuery(query, null);
        int highestId = 0;
        if (cursor.moveToFirst()) {
            highestId = cursor.getInt(0);
        }
        cursor.close();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname",firstname);
        contentValues.put("lastname",lastname);
        contentValues.put("idnumber", highestId + 1);
        contentValues.put("major",major);
        contentValues.put("gender",gender);
        contentValues.put("weight",weight);
        contentValues.put("date",date);


        long result=DB.insert("student", null, contentValues);

        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public boolean deleteAllEntries() {
        SQLiteDatabase DB = this.getWritableDatabase();
        try {
            // Delete all records from the "student" table
            int result1 = DB.delete("student", null, null);

            // Delete all records from the "units" table
            int result2 = DB.delete("units", null, null);

            // Return true if all deletions were successful
            return result1 > 0 && result2 > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Boolean updateuserdata(String firstname, String lastname, String idnumber, String major, String gender,String weight, String date){
        SQLiteDatabase DB= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname",firstname);
        contentValues.put("lastname",lastname);
        contentValues.put("idnumber",idnumber);
        contentValues.put("major",major);
        contentValues.put("gender",gender);
        contentValues.put("weight",weight);
        contentValues.put("date",date);
        Cursor cursor=DB.rawQuery("select * from student where idnumber = ?", new String[] {idnumber});
        if(cursor.getCount()>0) {
            long result = DB.update("student", contentValues, "idnumber=?", new String[]{idnumber});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

    public boolean deleteuserdata(String idnumber) {
        SQLiteDatabase DB = this.getWritableDatabase();

        // Delete from student table
        int result1 = DB.delete("student", "idnumber=?", new String[]{idnumber});

        // Delete from units table
        int result2 = DB.delete("units", "idnumber=?", new String[]{idnumber});


        // Return true if all deletions were successful
        return result1 > 0 && result2 > 0;
    }

        public Cursor getuserdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from student", null);
        return cursor;
    }

    // Methods for units table

    public Boolean insertfeesdata(String idnumber, String totalfees, String feespaid, String feesbalance, String clearancedate) {
        SQLiteDatabase DB = this.getWritableDatabase();

        int nextIdNumber = getNextAvailableIdNumber();

        ContentValues contentValues = new ContentValues();
        contentValues.put("idnumber", idnumber);
        contentValues.put("totalfees", totalfees);
        contentValues.put("feespaid", feespaid);
        contentValues.put("feesbalance", feesbalance);
        contentValues.put("clearancedate", clearancedate);


        long result = DB.insert("units", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updatefeesdata(String idnumber, String totalfees, String feespaid, String feesbalance, String clearancedate){
        SQLiteDatabase DB= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idnumber",idnumber);
        contentValues.put("totalfees",totalfees);
        contentValues.put("feespaid",feespaid);
        contentValues.put("feesbalance",feesbalance);
        contentValues.put("clearancedate",clearancedate);
        Cursor cursor=DB.rawQuery("select * from units where idnumber = ?", new String[] {idnumber});
        if(cursor.getCount()>0) {
            long result = DB.update("units", contentValues, "idnumber=?", new String[]{idnumber});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean deletefeesdata(String idnumber){
        SQLiteDatabase DB= this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("select * from student where idnumber = ?", new String[] {idnumber});
        if(cursor.getCount()>0) {
            long result = DB.delete("units", "idnumber=?", new String[]{idnumber});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }


        // Existing code...




    public Cursor getfeesdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from units", null);
        return cursor;
    }


    public Cursor getCombinedData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("SELECT " +
                "student.firstname, " +
                "units.idnumber, " +
                "student.major, " +
                "units.totalfees, " +
                "units.feesbalance, " +
                "units.clearancedate " +
                "FROM student " +
                "JOIN units ON student.idnumber = units.idnumber", null);
    }

    public int getNextAvailableIdNumber() {
        SQLiteDatabase DB = this.getReadableDatabase();
        String query = "SELECT MAX(idnumber) FROM student";
        Cursor cursor = DB.rawQuery(query, null);
        int highestId = 0;
        if (cursor.moveToFirst()) {
            highestId = cursor.getInt(0);
        }
        cursor.close();
        // Increment the highestId to get the next available idnumber
        return highestId + 1;
    }




}
