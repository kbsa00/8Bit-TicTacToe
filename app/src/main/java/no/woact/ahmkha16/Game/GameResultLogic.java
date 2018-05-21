package no.woact.ahmkha16.Game;

import java.util.HashMap;

/**
 * Created by Khalid B. Said on 3/25/2018.
 *
 * A simple GameResultLogic class. This class is intended to be used only for
 * checking whether the Game Board has a currently a winner on the table or not.
 */

public class GameResultLogic {

    private final Integer[][] mTicTacToeResults;


    public GameResultLogic(){
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
     * A method that checks if there's any winners on the board and comparing
     * the board to the ticTacToeResults 2D for answers.
     *
     * @param boardLog - current log over the entire BoardLog.
     * @return - returns an integer if theres a winner or not.
     */
    public Integer comparingBoardToResults(HashMap<Integer, Integer> boardLog){

        int checkingPlayerOneWinner = 0;
        int checkingPlayerTwoWinner = 0;

        if (boardLog.size() >= 5){
            Integer position = 0;

            for (int i = 0; i < mTicTacToeResults.length; i++){

                for (int j = 0; j < 3; j++){

                    position = mTicTacToeResults[i][j];

                    if (boardLog.get(position) != null && boardLog.get(position) == 1) checkingPlayerOneWinner++;
                    else if (boardLog.get(position) != null && boardLog.get(position) == 2) checkingPlayerTwoWinner++;
                }

                if (checkingPlayerOneWinner == 3 || checkingPlayerTwoWinner == 3){
                    return boardLog.get(position);
                }else {
                    checkingPlayerOneWinner = 0;
                    checkingPlayerTwoWinner = 0;
                }
            }
        }
        return 0;
    }
}
