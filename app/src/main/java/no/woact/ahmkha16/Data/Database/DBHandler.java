package no.woact.ahmkha16.Data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import no.woact.ahmkha16.Data.Database.Config.DBConfig;
import no.woact.ahmkha16.Data.Database.Helper.SQLiteHelperClass;
import no.woact.ahmkha16.Data.Model.Player;

/**
 * Created by Khalid B. Said
 * A simple Database Handler class that contains all of the CRUD operations against the Database.
 */


public class DBHandler{

    //Fields for the class
    private SQLiteDatabase mDB;
    private SQLiteHelperClass mSqldbHelper;
    private ArrayList<Player> mListOfPlayersDB;

    public DBHandler(Context context){
        mSqldbHelper = new SQLiteHelperClass(context);
        mListOfPlayersDB = new ArrayList<>();
    }

    /**
     * Simple method where we are adding the player to the Database.
     * @param player
     */
    public void addPlayer(Player player){
        mDB = mSqldbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBConfig.PLAYER_NAME, player.getPlayerName());
        values.put(DBConfig.PLAYER_WINS, player.getPlayerWins());
        mDB.insert(DBConfig.TABLE_NAME, null , values);

        mDB.close();
    }

    /**
     * A method that returns 10 players with the highest win scores from the database.
     * @return mListOfPlayersDB - 10 Highest win score players.
     */
    public ArrayList<Player> getAllPlayers(){

        //Taking precautions and deleting the list if its containing anything
        if (mListOfPlayersDB != null) {
            mListOfPlayersDB.clear();
        }

        mDB = mSqldbHelper.getReadableDatabase();

        String dbQuery = "SELECT * FROM " + DBConfig.TABLE_NAME + " WHERE " +
                DBConfig.PLAYER_WINS + " ORDER BY " + DBConfig.PLAYER_WINS + " DESC LIMIT " + 10;
        Cursor cursor = mDB.rawQuery(dbQuery, null);

        if (cursor.moveToNext()){

            do {
                Player player = new Player();
                player.setPlayerName(cursor.getString(cursor.getColumnIndex(DBConfig.PLAYER_NAME)));
                player.setPlayerWins(cursor.getInt(cursor.getColumnIndex(DBConfig.PLAYER_WINS)));
                mListOfPlayersDB.add(player);

            }while (cursor.moveToNext());
        }

        mDB.close();
        cursor.close();

        return mListOfPlayersDB;
    }

    /**
     * This method returns a specific player from the database based on the param.
     * @param playerName
     * @return player - Returns a player object from the DB.
     */
    public Player getPlayer(String playerName){

        mDB = mSqldbHelper.getReadableDatabase();

        String dbQuery = "SELECT * FROM " + DBConfig.TABLE_NAME + " WHERE " +
                DBConfig.PLAYER_NAME  + " = '" + playerName + "'";

        Cursor cursor = mDB.rawQuery(dbQuery, null);

        if (cursor.moveToFirst()){

            Player player = new Player();
            player.setPlayerName(cursor.getString(cursor.getColumnIndex(DBConfig.PLAYER_NAME)));
            player.setPlayerWins(cursor.getInt(cursor.getColumnIndex(DBConfig.PLAYER_WINS)));
            return player;
        }

        mDB.close();
        cursor.close();

        return null;
    }

    /**
     * This method updates a specific player based on the param.
     * Wins will be then updated in this method to the DB.
     * @param playersName
     * @param wins
     */
    public void updatePlayersScore(String playersName, int wins){

        mDB = mSqldbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBConfig.PLAYER_WINS, wins);
        mDB.update(DBConfig.TABLE_NAME, values, DBConfig.PLAYER_NAME + "= ?", new String[]{playersName});

        mDB.close();
    }


    /**
     * Simple method that deletes a specific player based on the param from the database
     * Note: This method is only used in the JUnit test right now.
     * @param playersName
     */
    public void deletePlayerFromDB(String playersName){

        mDB = mSqldbHelper.getReadableDatabase();

        String query = "DELETE FROM " + DBConfig.TABLE_NAME + " WHERE " +
                DBConfig.PLAYER_NAME + " = '" + playersName + "'";

        mDB.execSQL(query);

        mDB.close();
    }
}
