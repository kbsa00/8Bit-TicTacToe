package no.woact.ahmkha16.Game;

import java.util.HashMap;

/**
 * Created by Khalid B. Said
 *
 * This class is for Bots logic. The class is implemented with 3 easy methods.
 * Where the bot is able to calculate tactics for defending and attacking and also
 * finding the best place to start early in the game.
 */

public class BotLogic {

    private final Integer[][] mTicTacToeResults;

    public BotLogic() {
        //Setting up all possible way of winning in Tic Tac Toe
        //In a two dimensional Array.
        mTicTacToeResults = new Integer[][]{
                {0,1,2},
                {3,4,5},
                {6,7,8},
                {0,3,6},
                {2,5,8},
                {1,4,7},
                {2,4,6},
                {0,4,8}
        };
    }


    /**
     * A method created for the bot where it tries to find the best
     * calculated move for an attack against the opponent.
     */
    public Integer botsCalculatedMoveToDefend(HashMap<Integer, Integer> componentsHand, HashMap<Integer, Integer> boardlog){

        boolean islooking = true;

        while (islooking) {

            for (int i = 0; i < mTicTacToeResults.length; i++) {

                int counter = 0;
                int lastPos = 0;

                for (int j = 0; j < 3; j++) {

                    int position = mTicTacToeResults[i][j];

                    if (componentsHand.get(position) != null) {
                        counter++;
                    } else{
                        lastPos = position;
                    }
                }

                if (counter == 2) {
                    if (boardlog.get(lastPos) == null){
                        return lastPos;
                    }
                }
            }

            islooking = false;
        }

        return null;
    }


    /**
     * A method created for the bot where it tries to find the best
     * calculated move for an attack against the opponent.
     */

    public Integer botsCalculatedMoveToAttack(HashMap<Integer, Integer> botsHand, HashMap<Integer, Integer> boardLog){

        boolean isLooking = true;

        while (isLooking){

            for (int i = 0; i < mTicTacToeResults.length; i++){

                int counter = 0;
                int lastPos = 0;

                for (int j = 0; j < 3; j++){
                    int position = mTicTacToeResults[i][j];

                    if (botsHand.get(position) != null){
                        counter++;
                    }else {
                        lastPos = position;
                    }
                }

                if (counter == 2){

                    if (boardLog.get(lastPos) == null){
                        return lastPos;
                    }
                }
            }

            isLooking = false;
        }

        return null;
    }

    /**
     *  A method for checking the best start position for the bot and
     *  then proceeds to return the button number of the best moves to do
     *  early in the game.
     */
    public Integer checkBestStartPosition(HashMap<Integer, Integer> boardLog){

        if (boardLog.size() == 1 && boardLog.get(4) == null){
            return 4;
        }
        else if (boardLog.size() == 1 && boardLog.get(4) != null && boardLog.get(0) == null){
            return 0;
        }

        return null;
    }

    /**
     * Looks for a well known scenarios and then tries to stop the user
     * from pulling these through.
     */

    public Integer threeOnTheBoardScenarios(HashMap<Integer, Integer> boardLog){

        if (boardLog.get(4) != null && boardLog.get(0) != null && boardLog.get(8) != null) {

            if (boardLog.get(4) == 2 && boardLog.get(0) == 1 && boardLog.get(8) == 1) {
                return 7;
            }
            else if (boardLog.get(4) == 1 && boardLog.get(0) == 2 && boardLog.get(8) == 1){
                return 2;
            }

        }
        else if(boardLog.get(4) != null && boardLog.get(2) != null && boardLog.get(6) != null){

            if (boardLog.get(4) == 2 && boardLog.get(2) == 1 && boardLog.get(6) == 1) {
                return 1;
            }

        }
        else if (boardLog.get(4) != null && boardLog.get(7) != null && boardLog.get(3) != null){

            if (boardLog.get(4) == 2 && boardLog.get(7) == 1 && boardLog.get(3) == 1){
                return 0;
            }

        }
        else if (boardLog.get(4) != null && boardLog.get(7) != null && boardLog.get(5) != null){

            if (boardLog.get(4) == 2 && boardLog.get(7) == 1 && boardLog.get(5) == 1){
                return 2;
            }

        }
        else if (boardLog.get(4) != null && boardLog.get(2) != null && boardLog.get(3) != null){

            if (boardLog.get(4) == 2 && boardLog.get(3) == 1 && boardLog.get(2) == 1){
                return 0;
            }

        }

        return null;
    }


    /**
     * Looking for well known scenarios where there has been placed 2 marks on the table, and
     * then proceeds to try to prevent these from happening.
     */

    public Integer twoBoardsScenarios(HashMap<Integer, Integer> boardLog){

        if (boardLog.size() == 2) {
            if (boardLog.get(4) != null && boardLog.get(7) != null) {

                if (boardLog.get(4) == 2 && boardLog.get(7) == 1) {
                    return 5;
                }
            }
        }else if (boardLog.size() == 3){

            if (boardLog.get(4) != null && boardLog.get(7) != null){

                if (boardLog.get(0) != null){
                    return 5;
                }
                else if(boardLog.get(2) != null){
                    return 5;
                }
            }
        }

        return null;
    }


    /**
     * This method will proceed to look for well known fourBoard marks scenarios as I like to call it.
     * The method proceeds to help the bot to prevent these from happening.
     */
    public Integer fourBoardsScenarios(HashMap<Integer, Integer> boardLog){

        if (boardLog.get(4) != null && boardLog.get(7) != null && boardLog.get(5) != null){

            if (boardLog.get(4) == 2 && boardLog.get(7) == 1 && boardLog.get(5) == 2){
                return 0;
            }
        }

        return null;
    }
}
