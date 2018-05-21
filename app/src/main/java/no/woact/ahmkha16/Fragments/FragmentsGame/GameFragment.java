package no.woact.ahmkha16.Fragments.FragmentsGame;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.HashMap;
import no.woact.ahmkha16.Data.Model.Player;
import no.woact.ahmkha16.Game.GameLogic;
import no.woact.ahmkha16.Media.AudioPlayer;
import no.woact.ahmkha16.R;


/**
 * Description of the Class:
 * This GameFragment is a Fragment Class where the user will be met by
 * a gameBoard where the user will have the opportunity of actually playing the game
 * This class will handle if the user is playing a one player mode or two player mode.
 * The class will be using external/other classes for the handling of the tic tac toe logic.
 * What this class i meant for using is only for the interactions of the components and making
 * the visual of the game easy to understand for the user.
 *
 */

public class GameFragment extends Fragment implements View.OnClickListener{

    private HashMap<Integer, ImageButton> mImageButtons;
    private GameLogic mGameLogic;
    private TextView mPlayerOneheaderTxt;
    private TextView mPlayerTwoheaderTxt;
    private AudioPlayer mAudioPlayer;
    public static Player sPlayerOne;
    public static Player sPlayerTwo;
    private TextView mTimer;

    private long startTime = 0L, timeInMilliSeconds = 0L, updatedTime = 0L, timesSwapbUFF = 0L;
    private Handler mHandler;
    private Runnable mUpdateTimeThread;

    public GameFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        initilizeWidgets(view);
        setUpScoreBoard();
        setGameMode();
        setPlayerTurn();

        return view;
    }

    /**
     * This is just a simple method where it will place both of the players name
     * on the gamefragment.
     */
    public void setUpScoreBoard(){
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null){

            sPlayerOne = (Player) bundle.getSerializable("playerOne");
            sPlayerTwo = (Player) bundle.getSerializable("playerTwo");

            mPlayerOneheaderTxt.setText(sPlayerOne.getPlayerName());
            mPlayerTwoheaderTxt.setText(sPlayerTwo.getPlayerName());
        }
    }

    /**
     * A simple initialization method that will initialize every component the user will
     * interact with.
     * It will also create the objects that it needs to for starting a game or picking the starter
     * of the game.
     * @param view
     */
    public void initilizeWidgets(View view) {
        mPlayerOneheaderTxt = (TextView) view.findViewById(R.id.playerOneNameHeaderText);
        mPlayerTwoheaderTxt = (TextView) view.findViewById(R.id.playerTwoNameHeaderText);
        mTimer = (TextView) view.findViewById(R.id.timer);
        mImageButtons = new HashMap<>();
        mGameLogic = new GameLogic(getActivity());
        mAudioPlayer = AudioPlayer.getInstance();
        mHandler = new Handler();

        initilizeButtons(view);
    }

    /**
     * This is a simple method that will initialize every button that is on the board
     * and then procede to store them in a hashMap with a specific key in this case will
     * be the buttons number.
     * @param view
     */
    private void initilizeButtons(View view){
        Resources res = getResources();

        for(int i = 0; i < 9; i++){
            String imagebtnID = "imagebtn" + i;
            ImageButton currentBtn = view.findViewById(res.getIdentifier(imagebtnID, "id", getActivity().getPackageName()));
            mImageButtons.put(i, currentBtn);
            mImageButtons.get(i).setOnClickListener(this);
        }
    }

    /**
     * This method will set the GameMode of the game, so thats easier for other classes
     * to know what game mode the user is currently playing.
     */
    public void setGameMode(){
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null){
            mGameLogic.setGameModeLogic(String.valueOf(bundle.get("Mode")));
        }

        mGameLogic.setImageButtons(mImageButtons);
    }

    /**
     * This method will set the players turn based on what the user earlier picked
     * before entering the GameFragment.
     */
    public void setPlayerTurn(){
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) mGameLogic.setPlayerTurn(bundle.getBoolean("playerStarter"));

        if (!bundle.getBoolean("playerStarter") && mGameLogic.getGameModeLogic().equals("Player One Mode")) mGameLogic.botsLogicForPicking();

    }

    /**
     * A simple On Click method that picks up everytime the user has clicked on a
     * button that is placed on the gameboard. This method will procede to
     * send the acutal view of the button to their respective classes that will handle
     * their actions. This method will also check if theres a winner or not and then,
     * procede to put all of the buttons not beeing enabled.
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (mGameLogic.isGameStateOver()){
            for (int i = 0; i < mImageButtons.size(); i++){
                mImageButtons.get(i).setEnabled(false);
            }
        }else {
            //Audio for everytime the user has pressed a button.
            mAudioPlayer.startButtonPressedAudio(getActivity().getApplicationContext(), R.raw.buttonpressed);

            //Sending the view and it's respective keyvalue to GameLogic class that will handle the games logic.
            int keyValue = getClickedButton(v.getResources().getResourceName(v.getId()));
            mGameLogic.presentPlayersButtonChoice(v, keyValue);
        }


        if (mGameLogic.isGameStateOver()){
            stopTheWatchTimer();
        }
    }

    /**
     * This method takes the imageButton's idname and then procede to return the buttons keyValue
     * @param buttonIDname
     * @return
     */
    public int getClickedButton(String buttonIDname){
        int buttonNumber;
        String buttonKey = String.valueOf(buttonIDname.charAt(buttonIDname.length()-1));
        return buttonNumber = Integer.parseInt(buttonKey);
    }

    /**
     * This is just a simple method that allows us to stop the timer for us.
     */
    private void  stopTheWatchTimer(){
        timesSwapbUFF += timeInMilliSeconds;
        mHandler.removeCallbacks(mUpdateTimeThread);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTheWatchTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        startTime = SystemClock.uptimeMillis();

        mUpdateTimeThread = new Runnable() {
            @Override
            public void run() {
                timeInMilliSeconds = SystemClock.uptimeMillis() - startTime;
                updatedTime = timesSwapbUFF + timeInMilliSeconds;

                int sec = (int) (updatedTime / 1000);
                int mins = sec / 60;
                sec %= 60;

                //I have to do this operation because Android is telling me not to concat strings in setText..
                String time = String.valueOf(mins) + ":" +  String.valueOf(sec);
                mTimer.setText(time);
                mHandler.postDelayed(this, 0);
            }
        };
        mHandler.postDelayed(mUpdateTimeThread, 0);
    }
}

