package my.awesome.lavanya.sciencetriviaforkids;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by lavanya on 2/26/17.
 */

public class Databaseopener extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "sciencequiz.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = Databaseopener.class.getSimpleName();
    private static String DB_PATH = "";
    private Context mcontext;

    public Databaseopener(Context context, String name, String storageDirectory, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, null, DATABASE_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        mcontext = context;
    }
    public class copydata extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                boolean dbExist = checkDataBase();

                if (dbExist) {

                    //do nothing - database already exist
                }
                else {


                    InputStream mInput = mcontext.getAssets().open(DATABASE_NAME);
                    DBHandler dbhelper = new DBHandler(mcontext);
                    dbhelper.getWritableDatabase();
                    String outFileName = DB_PATH + DATABASE_NAME;
                    OutputStream mOutput = new FileOutputStream(outFileName);
                    byte[] mBuffer = new byte[1024];
                    int mLength;
                    while ((mLength = mInput.read(mBuffer)) > 0) {
                        Log.d(TAG, "copying");
                        mOutput.write(mBuffer, 0, mLength);
                    }
                    mOutput.flush();
                    mOutput.close();
                    mInput.close();

                    dbhelper.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }


            return null;
        }


    }

    public void copyDataBase() throws IOException {
        copydata copydatas = new copydata();
        copydatas.execute();
    }

    private boolean checkDataBase(){


        //   File databasePath = mcontext.getDatabasePath(DATABASE_NAME);
        //   return databasePath.exists();
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }
}
