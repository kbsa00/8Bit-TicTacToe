package no.woact.ahmkha16.Fragments.FragmentOptions;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import no.woact.ahmkha16.Activities.FrameActivity;
import no.woact.ahmkha16.Data.Database.DBHandler;
import no.woact.ahmkha16.Data.Model.Player;
import no.woact.ahmkha16.Game.GameNotifiers.StarterPlayerAlert;
import no.woact.ahmkha16.Game.GameNotifiers.UserExistAlert;
import no.woact.ahmkha16.Interfaces.InterfaceDatabase.DatabaseMethods;
import no.woact.ahmkha16.Media.AudioPlayer;
import no.woact.ahmkha16.R;

/**
 * Description of the Class:
 *
 * The TwoPlayerMode fragment Class is constructed for the Two Player Mode
 * that user had an option of picking. The Class will save the players
 * name and then ask the user weather the player 1 or player 2 should start the game.
 * After this process the user will go on to play the actual game.
 *
 */
public class TwoPlayerMode extends Fragment implements View.OnClickListener, DatabaseMethods {

    //Fields of the class.
    private TextView mHeadlineTxt;
    private EditText mEnternameEdittext;
    private TextView mHeaderfragmenText;
    private Button mStartGameBtn;
    private Intent mIntent;
    private AlertDialog.Builder mBuilder;
    private AudioPlayer mAudioPlayer;
    private StarterPlayerAlert mStarterPlayerAlert;
    private DBHandler mDB;
    private String mTempName = "";
    private UserExistAlert mUserExistAlert;
    private ArrayList<Player> mListOfPlayers;



    public TwoPlayerMode() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_two_player_mode, container, false);
        initialization(view);

        return view;
    }

    /**
     * A simple initialization method that will initialize every component the user will
     * interact with.
     * It will also create the objects that it needs to for starting a game or picking the starter
     * of the game.
     * @param view
     */
    public void initialization(View view){

        mEnternameEdittext = (EditText) view.findViewById(R.id.enternametext);
        mStartGameBtn = (Button) view.findViewById(R.id.start1v1gameBtn);
        mHeaderfragmenText = (TextView) view.findViewById(R.id.playerheadtxt);
        mHeadlineTxt = (TextView) getActivity().findViewById(R.id.headerTxtMainActivity);
        mHeadlineTxt.setText(getResources().getString(R.string.menyPlayerVPlayerText));
        mStartGameBtn.setOnClickListener(this);
        mAudioPlayer = AudioPlayer.getInstance();

        mIntent = new Intent(getActivity(), FrameActivity.class);
        mBuilder = new AlertDialog.Builder(getActivity());
        mStarterPlayerAlert = new StarterPlayerAlert();
        mUserExistAlert = new UserExistAlert();
        mDB = new DBHandler(getActivity().getApplicationContext());
        mListOfPlayers = new ArrayList<>();
    }

    /**
     * This is a simple method. OnClick method that will handle the when the add and start game is
     * pressed.
     * This method will also validate if the length plaeyers name complies with the rule that we have set.
     * We are also writing the code so that  user ends up using the same editTexts. So after
     * player one is added the method makes it self ready for player two
     */

    @Override
    public void onClick(View v) {

        if (mEnternameEdittext.getText().toString().toLowerCase().equals("tttbot")){
            Toast.makeText(getActivity(), "Nice try! You can't play as the bot! ", Toast.LENGTH_SHORT).show();
        }
        else if (!mEnternameEdittext.getText().toString().isEmpty() && mEnternameEdittext.getText().toString().length() < 8
                && mEnternameEdittext.getText().toString().length() > 3){

            mAudioPlayer.startButtonPressedAudio(getActivity().getApplicationContext(), R.raw.buttonpressed);

            String playerName = mEnternameEdittext.getText().toString().toUpperCase();

            Player player = checkIfUserAlreadyExist(playerName);

            if(player == null) {

                addPlayerToDB(playerName);

            } else if (mListOfPlayers.size() == 2){
                 settingUpTheGame(mListOfPlayers.get(0), mListOfPlayers.get(1));
            }

        }else {
            Toast.makeText(getActivity(), "Player name is either to long, short or empty, Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This player will simply add the new player into the Database and will soon after that
     * start to set up the game.
     * @param playerName
     */
    @Override
    public void addPlayerToDB(String playerName) {
        Player player = new Player(playerName, 0);

        mDB.addPlayer(player);
        mListOfPlayers.add(player);

        if (mListOfPlayers.size() == 2){
            settingUpTheGame(mListOfPlayers.get(0), mListOfPlayers.get(1));
        }else {
            settingUpforPlayerTwoInput();
        }
    }

    @Override
    public Player checkIfUserAlreadyExist(String playerName) {
        Player player = mDB.getPlayer(playerName);

        if (player != null && !mTempName.equals(playerName)){
            mUserExistAlert.settingUpTheCustomAlertDialog(getActivity(), R.layout.play_last_player_alertdialog, mBuilder);
            mTempName = player.getPlayerName();
        }
        else if (mTempName.equals(playerName)){
            mListOfPlayers.add(player);
            settingUpforPlayerTwoInput();
        }

        return player;
    }

    /**
     * This method will start setting up the input for Player Two.
     */
    private void settingUpforPlayerTwoInput() {
        mHeaderfragmenText.setText(getResources().getString(R.string.HeadlinePlayerTwo_txt));
        mStartGameBtn.setText(getResources().getString(R.string.startGamebtn_txt));
        mEnternameEdittext.setText("");
    }

    private void settingUpNewRoundOfInput(){
        mHeaderfragmenText.setText(getResources().getString(R.string.HeadlinePlayerOne_txt));
        mStartGameBtn.setText(getResources().getString(R.string.addplayer_txt));
        mEnternameEdittext.setText("");
    }

    /**
     * This method will start setting up the game for the user. You will first have the option of
     * picking a player starter. After this is done that the Tic Tac Toe game starts.
     * Methods will also send over both of the player objects.
     * @param playerOne
     * @param playerTwo
     */
    private void settingUpTheGame(Player playerOne, Player playerTwo){

        if (playerOne.getPlayerName().toLowerCase().equals(playerTwo.getPlayerName().toLowerCase())){
            Toast.makeText(getActivity(), "Two players with the same. That does not work :) Try again", Toast.LENGTH_SHORT).show();
            mListOfPlayers.clear();
            settingUpNewRoundOfInput();
        }else{
            mIntent.putExtra("FragKey", "GameFragment");
            mIntent.putExtra("playerTwo",playerTwo);
            mIntent.putExtra("playerOne", playerOne);
            mIntent.putExtra("Mode", "Player Two Mode");

            //Setting up an custom alert dialog where the user has to pick which player should start.
            mStarterPlayerAlert.settingUpTheCustomAlertDialog(R.layout.pick_starter_alertdialog, mBuilder, mIntent, getActivity(),
                    playerOne.getPlayerName(), playerTwo.getPlayerName());
            mListOfPlayers.clear();
            settingUpNewRoundOfInput();
        }
    }
}
