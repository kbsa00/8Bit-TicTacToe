package no.woact.ahmkha16.Game;

import java.util.HashMap;


/**
 * Created by Khalid B. Said on 3/17/2018.
 *
 * A simple GameObserver class that logs everything from which player has pressed a button
 * and also logging the current state of the Game Board.
 * The class is small and is used what it is intended to be used for.
 */

public class GameObserver {
    private HashMap <Integer, Integer> mPlayerOnesChoices;
    private HashMap <Integer, Integer> mPlayerTwoesChoices;
    private HashMap <Integer, Integer> mBotChoices;
    private HashMap <Integer, Integer> mGameBoardLog;

    /**
     * A simple and straight forward constructor that initializes the hashMaps.
     */
    public GameObserver(){
        mPlayerOnesChoices = new HashMap<>();
        mPlayerTwoesChoices = new HashMap<>();
        mBotChoices = new HashMap<>();
        mGameBoardLog = new HashMap<>();
    }

    public HashMap<Integer, Integer> getPlayerOnesChoices() {
        return mPlayerOnesChoices;
    }

    public HashMap<Integer, Integer> getPlayerTwoesChoices() {
        return mPlayerTwoesChoices;
    }

    public HashMap<Integer, Integer> getBotChoices() {
        return mBotChoices;
    }

    public HashMap<Integer, Integer> getGameBoardLog(){
        return mGameBoardLog;
    }

    /**
     * Simple log method for Player One
     */
    public void setPlayerOnesChoices(int key){
        mPlayerOnesChoices.put(key, 1);
        updateGameBoardLog(key, 1);
    }

    /**
     * Simple log method for Player Two.
     */
    public void setPlayerTwoesChoices(int key){
        mPlayerTwoesChoices.put(key, 2);
        updateGameBoardLog(key, 2);
    }

    /**
     * Simple log method for botChoices.
     * @param key
     */
    public void setBotChoices(int key){
        mBotChoices.put(key, 2);
        updateGameBoardLog(key, 2);
    }

    /**
     * Simple BoardLog method that logs every button that has been pressed,
     * and also logging which button has been pressed and by which player.
     * @param buttonKey - Is in this case the which button has been pressed
     * @param playerID  - Is an ID on which player it has been pressed by.
     */
    public void updateGameBoardLog(int buttonKey, int playerID ){
        mGameBoardLog.put(buttonKey, playerID);
    }
}
