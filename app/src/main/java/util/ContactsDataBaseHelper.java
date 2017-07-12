package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by doersweb on 12/07/17.
 */

public class ContactsDataBaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "sms.db";
    // If we change the database schema, we must increment the database version.
    private static final int DATABASE_VERSION = 1;

    //These are the fields our table will have
    public static final String NAME = "name";
    public static final String TIME = "time";
    public static final String OTP = "otp";
    public static final String MOB_NUM = "mobile";

    //Table where we will store the data for messages sent
    public static final String TABLE_NAME = "history";


    public ContactsDataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_SMS_HISTORY_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" +
                NAME + " TEXT," +
                TIME + " TEXT," +
                OTP + " INTEGER," +
                MOB_NUM + " TEXT"+
                ")";

        sqLiteDatabase.execSQL(CREATE_SMS_HISTORY_TABLE);

    }


    //This is used when we update the schema
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    long result = -1;

    //Method to insert the data into the database
    public boolean insertData(String name, int otp, String time, String mobile){
        try {

            // Gets the data repository in write mode
            SQLiteDatabase database = this.getWritableDatabase();
            database.beginTransaction();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(NAME, name);
                contentValues.put(TIME, time);
                contentValues.put(OTP, otp);
                contentValues.put(MOB_NUM, mobile);

                // Insert the new row with the value in contentValues
                result = database.insertOrThrow(TABLE_NAME, null, contentValues);
                database.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                database.endTransaction();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    //Method to retrieve the data from the database
    public Cursor getSmsHistoryData(){

        // Gets the data repository in write mode
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME, null);
        return result;
    }

}
