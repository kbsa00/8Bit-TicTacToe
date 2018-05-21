package no.woact.ahmkha16.Game;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import java.util.HashMap;
import java.util.Random;

import no.woact.ahmkha16.Fragments.FragmentsGame.GameFragment;
import no.woact.ahmkha16.Game.GameNotifiers.AfterGameAlert;
import no.woact.ahmkha16.R;

/**
 * Created by Khalid B. Said on 3/23/2018.
 *
 * Description of the Class:
 * This GameLogic class is self explanatory. The class will take care of
 * the Tic Tac Toe game logic like playerTurns and placing the right picture
 * for the button based on players turn and checking if there's a winner or not.
 * The class will also procede to use GameObserver class
 * to log every single button that has been placed on the board.
 *
 * This class will also contain the bot's way of chosing the
 */

public class GameLogic {

    private boolean mPlayerTurn;
    private String mGameModeLogic;
    private GameObserver mGameObserver;
    private AfterGameAlert mAfterGameAlert;
    private Handler mHandler;
    private HashMap<Integer, ImageButton> mImageButtons;
    private GameResultLogic mGameResultLogic;
    private Activity mActivity;
    private AlertDialog.Builder mBuilder;
    private boolean mGameStateOver;
    private BotLogic mBotlogic;

    /**
     * Simple constructor for the class, All of the instantiating objects and
     * giving the variables their respective values happens in here.
     */
    public GameLogic(Activity mActivity){
        this.mActivity = mActivity;
        mGameObserver = new GameObserver();
        mGameResultLogic = new GameResultLogic();
        mHandler = new Handler();
        mImageButtons = new HashMap<>();
        mBotlogic = new BotLogic();
        mAfterGameAlert = new AfterGameAlert(mActivity);
        mGameStateOver = false;
    }

    /**
     * A setter method for the imageButtons that are placed on the board.
     */
    public void setImageButtons(HashMap<Integer, ImageButton> mImageButtons) {
        this.mImageButtons = mImageButtons;
    }


    public boolean isPlayerTurn() {
        return mPlayerTurn;
    }

    public void setPlayerTurn(boolean mPlayerTurn) {
        this.mPlayerTurn = mPlayerTurn;
    }

    public String getGameModeLogic() {
        return mGameModeLogic;
    }

    public void setGameModeLogic(String mGameModeLogic) {
        this.mGameModeLogic = mGameModeLogic;
    }

    public boolean isGameStateOver(){
        return mGameStateOver;
    }

    public void setGameStateOver(boolean mGameStateOver){
        this.mGameStateOver = mGameStateOver;
    }


    /**
     * This is a method that will present the players choice after clicking
     * on one of the buttons on the board. The method procede's to find out
     * first of all what game Mode the user is currently playing and then showing the user
     * a visual of the button that has been placed.
     *
     * @param view - ImageButtons view
     * @param keyValue - The ImageButtons keyValue
     */
    public void presentPlayersButtonChoice(View view, int keyValue){

        switch (getGameModeLogic()){

            case "Player One Mode":

                if (isPlayerTurn()){
                    //Player One will always be the red mushrooms on the board
                    view.setBackgroundResource(R.drawable.mushroomred);
                    //gameObserver will log player one's choices
                    mGameObserver.setPlayerOnesChoices(keyValue);
                    view.setEnabled(false);
                    setPlayerTurn(false);
                    //Procedes to pick a button for the bot
                    botsLogicForPicking();
                }
                else{
                    //Player Two will always be the green mushrooms on the board
                    view.setBackgroundResource(R.drawable.mushroomgreen);
                    view.setEnabled(false);
                    setPlayerTurn(true);
                }

                break;

            case "Player Two Mode":

                if (isPlayerTurn()){
                    //Player One will always be the red mushrooms on the board
                    view.setBackgroundResource(R.drawable.mushroomred);

                    //GameObserver will log player ones choices
                    mGameObserver.setPlayerOnesChoices(keyValue);
                    view.setEnabled(false);
                    setPlayerTurn(false);

                } else {
                    //Player Two will always be the green mushrooms on the board
                    view.setBackgroundResource(R.drawable.mushroomgreen);
                    mGameObserver.setPlayerTwoesChoices(keyValue);
                    view.setEnabled(false);
                    setPlayerTurn(true);
                }

                break;
        }

        checkingForCurrentWinner();
    }

    /**
     * This method is for the bots logic for picking. We are also using a extended
     * class that helps us alot of the process of knowing what to pick.
     * Everything from choosing the best start position to Attacking
     * and Defending are all used here.
     */
    public void botsLogicForPicking(){

        //Preventing the user to spam the buttons while bot is picking
        setButtonsEnabledFalse();

         mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean islookingForChoice = true;

                while (islookingForChoice){


                    //Finding the best position to start if the Player one starts the game.
                    if (mGameObserver.getPlayerOnesChoices().size() == 1){
                        Integer choiceStart = mBotlogic.checkBestStartPosition(mGameObserver.getGameBoardLog());

                        if (choiceStart != null){
                            mGameObserver.setPlayerTwoesChoices(choiceStart);
                            setButtonsEnabledTrue();
                            mImageButtons.get(choiceStart).performClick();
                            break;
                        }
                    }

                    //Placing the mark in the best position if the bots starts the game.
                    if (mGameObserver.getGameBoardLog().size() == 0){
                        mGameObserver.setPlayerTwoesChoices(4);
                        setButtonsEnabledTrue();
                        mImageButtons.get(4).performClick();
                        break;
                    }

                    //Calculated move for attack when push comes to shove
                    if (mGameObserver.getPlayerTwoesChoices().size() >=2){
                        Integer choice = mBotlogic.botsCalculatedMoveToAttack(mGameObserver.getPlayerTwoesChoices(), mGameObserver.getGameBoardLog());

                        if (choice != null){
                            mGameObserver.setPlayerTwoesChoices(choice);
                            setButtonsEnabledTrue();
                            mImageButtons.get(choice).performClick();
                            break;
                        }

                    }


                    //Calculated move for defending when push comes to shove.
                    if (mGameObserver.getPlayerOnesChoices().size() >=2){
                        Integer choice =  mBotlogic.botsCalculatedMoveToDefend(mGameObserver.getPlayerOnesChoices(), mGameObserver.getGameBoardLog());

                        if (choice != null){
                            mGameObserver.setPlayerTwoesChoices(choice);
                            setButtonsEnabledTrue();
                            mImageButtons.get(choice).performClick();
                            break;
                        }
                    }


                    if (mGameObserver.getGameBoardLog().size() == 3){
                        Integer secondChoice = mBotlogic.threeOnTheBoardScenarios(mGameObserver.getGameBoardLog());

                        if (secondChoice != null){
                            mGameObserver.setPlayerTwoesChoices(secondChoice);
                            setButtonsEnabledTrue();
                            mImageButtons.get(secondChoice).performClick();
                            break;
                        }

                    }

                    if (mGameObserver.getGameBoardLog().size() == 2 || mGameObserver.getGameBoardLog().size() == 3){
                        Integer secondchoice = mBotlogic.twoBoardsScenarios(mGameObserver.getGameBoardLog());

                        if (secondchoice != null){
                            mGameObserver.setPlayerTwoesChoices(secondchoice);
                            setButtonsEnabledTrue();
                            mImageButtons.get(secondchoice).performClick();
                            break;
                        }

                    }

                    if (mGameObserver.getGameBoardLog().size() == 4){
                        Integer thirdPickCounterattack = mBotlogic.fourBoardsScenarios(mGameObserver.getGameBoardLog());

                        if (thirdPickCounterattack != null){
                            mGameObserver.setPlayerTwoesChoices(thirdPickCounterattack);
                            setButtonsEnabledTrue();
                            mImageButtons.get(thirdPickCounterattack).performClick();
                            break;
                        }

                    }


                    //If neither of these works, then pick random. This will almost always work to our favour.
                   islookingForChoice = pickRandom();
                }

            }
        }, 800);
    }


    /**
     * Method for picking a random spot on the board for the bot.
     * Then return us a boolean if it has found one or not.
     */

    public boolean pickRandom(){
        Random rnd = new Random();
        int checkPickedChoiceisAvailble = 0;
        boolean islookingForChoice = true;

        while (islookingForChoice) {

            checkPickedChoiceisAvailble = rnd.nextInt(mImageButtons.size());

            if (!mGameObserver.getGameBoardLog().containsKey(checkPickedChoiceisAvailble)) {
                islookingForChoice = false;
                mGameObserver.setPlayerTwoesChoices(checkPickedChoiceisAvailble);
                //Enabling the buttons and excuting the click for button.
                setButtonsEnabledTrue();
                mImageButtons.get(checkPickedChoiceisAvailble).performClick();
            }

            if (mGameObserver.getGameBoardLog().size() == mImageButtons.size()) {
                islookingForChoice = false;
            }
        }
        return islookingForChoice;
    }

    /**
     * This is just a simple method for checking if there's any current
     * winner on the board or if there's a draw between both players.
     */
    private void checkingForCurrentWinner(){

       Integer winner = mGameResultLogic.comparingBoardToResults(mGameObserver.getGameBoardLog());

       if (winner == 1){
           mAfterGameAlert.updatePlayerWinnersScoreToTheDatabase(GameFragment.sPlayerOne);
           popUpAlertDialog("The Winner is " + GameFragment.sPlayerOne.getPlayerName().toUpperCase());
       }
       else if (winner == 2){
           mAfterGameAlert.updatePlayerWinnersScoreToTheDatabase(GameFragment.sPlayerTwo);
           popUpAlertDialog("The Winner is " + GameFragment.sPlayerTwo.getPlayerName().toUpperCase());
       }
       else if (winner == 0 && mGameObserver.getGameBoardLog().size() == mImageButtons.size()){
           popUpAlertDialog("IT'S A DRAW");
       }

    }

    /**
     * A simple method that starts a custom alert dialog
     * letting the user know who has won or if its a draw. Also letting the
     * user decide whether to play a new round or not.
     * @param message
     */
    private void popUpAlertDialog(String message){
        setGameStateOver(true);
        mBuilder = new AlertDialog.Builder(mActivity);
        mAfterGameAlert.settingUpTheCustomAlertDialog(R.layout.aftergame_alertdialog, mBuilder, message);
    }

    /**
     * Disabling all buttons that are not already been picked, So
     * that the user can't press any buttons while bot is picking.
     */
    private void setButtonsEnabledFalse(){
        HashMap<Integer, Integer> currentBoardLog = mGameObserver.getGameBoardLog();

        for (int i = 0; i < mImageButtons.size(); i++){

            if (currentBoardLog.get(i) == null){
                mImageButtons.get(i).setEnabled(false);
            }
        }
    }

    /**
     * Enabling all buttons that are not already been picked, So that
     * the bot and user can eventually pick them when it's their turn.
     */
    private void setButtonsEnabledTrue(){
        HashMap<Integer, Integer> currentBoardLog = mGameObserver.getGameBoardLog();

        for (int i = 0; i < mImageButtons.size(); i++) {

            if (currentBoardLog.get(i) == null){
                mImageButtons.get(i).setEnabled(true);
            }
        }
    }
}
