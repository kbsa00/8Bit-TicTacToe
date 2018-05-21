package no.woact.ahmkha16.Data.Database.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import no.woact.ahmkha16.Data.Database.Config.DBConfig;

/**
 * Created by Eier on 3/29/2018.
 */

public class SQLiteHelperClass extends SQLiteOpenHelper {

    public SQLiteHelperClass(Context context) {
        super(context, DBConfig.DATABASE_NAME, null, DBConfig.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " +
                DBConfig.TABLE_NAME + "(" + DBConfig.KEY_ID + " INTEGER PRIMARY KEY, " +
                DBConfig.PLAYER_NAME + " TEXT, " + DBConfig.PLAYER_WINS + " INT);";

        db.execSQL(CREATE_TABLE);

        ContentValues values = new ContentValues();
        values.put(DBConfig.PLAYER_NAME, "TTTBOT");
        values.put(DBConfig.PLAYER_WINS, 0);

        db.insert(DBConfig.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConfig.DATABASE_NAME);

        //Creating a new db for us.
        onCreate(db);
    }
}
