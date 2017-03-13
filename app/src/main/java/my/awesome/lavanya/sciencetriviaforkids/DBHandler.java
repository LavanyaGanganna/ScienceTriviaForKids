package my.awesome.lavanya.sciencetriviaforkids;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavanya on 2/26/17.
 */

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "sciencequiz.db";
    // Contacts table name
    private static final String TABLE_NAME = "sciencequest";
    // Shops Table Columns names

    private static final String TAG = DBHandler.class.getSimpleName();

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

/*
    public void addcelebrity(QuizObject quizObject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_CELEB, quizObject.getCelebid());
        values.put(KEY_NAME, quizObject.getCelebname());
        values.put(KEY_IMAGE,quizObject.getImageInbyte());
        //values.put(KEY_ADDR, quizObject.getCeleburl());

// Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }*/

    public List<QuizObject> getcelebrity(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        QuizObject contact = null;
        List<QuizObject> arrayList=new ArrayList<>(25);
        //  Cursor cursors= db.rawQuery(" SELECT * FROM celebrity LIMIT " + id +","+ " 25",null);
        Cursor cursors= db.rawQuery(" SELECT * FROM sciencequest LIMIT 25 OFFSET " + id ,null);
        //Cursor cursor = db.rawQuery(" SELECT * FROM celebrity WHERE  " + KEY_ID + "=?;", new String[]{Integer.toString(id)});
        if (cursors != null) {
            cursors.moveToFirst();
            // int i=id;
            while(!cursors.isAfterLast()){
                contact = new QuizObject(cursors.getString(1), cursors.getString(2),cursors.getString(3),cursors.getString(4),cursors.getString(5),cursors.getString(6));
                arrayList.add(contact);
                cursors.moveToNext();
            }

        }
        if(cursors!=null)
            cursors.close();
        //return contact;
        return  arrayList;
    }

}
