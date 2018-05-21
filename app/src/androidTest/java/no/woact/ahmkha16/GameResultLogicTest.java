package no.woact.ahmkha16;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import no.woact.ahmkha16.Game.GameResultLogic;

import static org.junit.Assert.assertEquals;

/**
 * Created by Khalid B. Said
 *
 * This Test class will check our GameResultLogic class. This class mainly determains
 * if there's a any winner, looser or draw on the gameBoard that has been sent in.
 */

public class GameResultLogicTest {

    private  HashMap<Integer, Integer> mBoardLogTestWin = new HashMap<>();
    private  HashMap<Integer, Integer> mBoardLogTestDraw = new HashMap<>();
    private  GameResultLogic mgameResultLogic;

    @Before
    public  void settingUpTest(){
        mgameResultLogic = new GameResultLogic();

        //Setting up the Winner HashMap.
        mBoardLogTestWin.put(0,1);
        mBoardLogTestWin.put(1,2);
        mBoardLogTestWin.put(3,1);
        mBoardLogTestWin.put(4,2);
        mBoardLogTestWin.put(6,1);

        //Setting up the Draw HashMap.
        mBoardLogTestDraw.put(0, 1);
        mBoardLogTestDraw.put(1, 2);
        mBoardLogTestDraw.put(2, 1);
        mBoardLogTestDraw.put(3, 2);
        mBoardLogTestDraw.put(4, 1);
        mBoardLogTestDraw.put(5, 2);
        mBoardLogTestDraw.put(6, 2);
        mBoardLogTestDraw.put(7, 1);
        mBoardLogTestDraw.put(8, 2);
    }


    @Test
    public void checkWinnerLogicTest(){
        //1 is always player one, Player one is always "You"
        Integer expextedWinner = 1;
        Integer actualWinner = mgameResultLogic.comparingBoardToResults(mBoardLogTestWin);
        assertEquals(expextedWinner, actualWinner);
    }

    @Test
    public void checkDrawTestLogic(){
        // 0 indecates a draw in the game.
        Integer expectedResult = 0;
        Integer resultFromTheLogic = mgameResultLogic.comparingBoardToResults(mBoardLogTestDraw);
        assertEquals(expectedResult, resultFromTheLogic);
    }
}
