package com.example.abdulrahman190194;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class UsersDB extends SQLiteOpenHelper {

    //////////////DATABASE NAME///////////////
    public static final String DATABASE_NAME = "Users.db";

    ///////////////USER TABLE///////////////
    public static final String USER_TABLE_NAME = "userstable";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_FIRST_NAME = "fname";
    public static final String USER_COLUMN_LAST_NAME = "lname";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_PHONE = "phone";
    public static final String USER_COLUMN_USERID = "userid";

    public UsersDB(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        /////////queries to create tables////////////////
        db.execSQL("create table userstable " + "(id integer primary key, date text, fname text, lname text, email text, phone text, userid text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS userstable");
        onCreate(db);
    }

    /////////////////////////////////////////////
    //////////Database Queries////////////
    /////////////////////////////////////////////

    public boolean insertUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("userid", user.getUserId());
        contentValues.put("fname", user.getFirstName());
        contentValues.put("lname", user.getLastName());
        contentValues.put("email", user.getEmailAddress());
        contentValues.put("phone", user.getPhoneNumber());

        db.insert("userstable", null, contentValues);

        return true;

    }

    public Integer deletUser(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("userstable",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public boolean updateUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("userid", user.getUserId());
        contentValues.put("fname", user.getFirstName());
        contentValues.put("lname", user.getLastName());
        contentValues.put("email", user.getEmailAddress());
        contentValues.put("phone", user.getPhoneNumber());

        db.update("userstable", contentValues, "id = ? ", new String[] { Integer.toString(user.getUserId()) } );

        return true;

    }

    public ArrayList<User> getUsersList() {
        ArrayList<User> userArrayList = new ArrayList<User>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from userstable", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            User user = new User();

            user.setUserId(res.getInt(res.getColumnIndex(USER_COLUMN_ID)));
            user.setFirstName(res.getString(res.getColumnIndex(USER_COLUMN_FIRST_NAME)));
            user.setLastName(res.getString(res.getColumnIndex(USER_COLUMN_LAST_NAME)));
            user.setEmailAddress(res.getString(res.getColumnIndex(USER_COLUMN_EMAIL)));
            user.setPhoneNumber(res.getString(res.getColumnIndex(USER_COLUMN_PHONE)));

            userArrayList.add(user);
            res.moveToNext();

        }

        return userArrayList;

    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from userstable where id="+id+"", null );

        if (res != null)
        {
            res.moveToFirst();

            User user = new User();

            user.setUserId(res.getInt(res.getColumnIndex(USER_COLUMN_ID)));
            user.setFirstName(res.getString(res.getColumnIndex(USER_COLUMN_FIRST_NAME)));
            user.setLastName(res.getString(res.getColumnIndex(USER_COLUMN_LAST_NAME)));
            user.setEmailAddress(res.getString(res.getColumnIndex(USER_COLUMN_EMAIL)));
            user.setPhoneNumber(res.getString(res.getColumnIndex(USER_COLUMN_PHONE)));

            return user;
        }
        else {
            return null;
        }
    }

}
