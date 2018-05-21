package no.woact.ahmkha16.Fragments.FragmentOptions;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
 * The OnePlayerMode fragment Class is constructed for the One Player Mode
 * that user had an option of picking. The Class will save the player's
 * name and then ask the user weather the bot or the player should start the game.
 * After this process the user will go on to play the actual game.
 *
 */
public class OnePlayerMode extends Fragment implements View.OnClickListener, DatabaseMethods {

    private EditText mEnterNameEdittxt;
    private Button mStartGameBtn;
    private TextView mHeadlinetext;
    private Intent mIntent;
    private AlertDialog.Builder mBuilder;
    private AudioPlayer mAudioPlayer;
    private StarterPlayerAlert mStarterPlayerAlert;
    private String mPlayerOneName;
    private UserExistAlert mUserExistAlert;
    private DBHandler mDB;
    private String mTempName = "";


    public OnePlayerMode() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_one_player_mode, container, false);
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
        mEnterNameEdittxt = (EditText) view.findViewById(R.id.playeredittxt);
        mStartGameBtn = (Button) view.findViewById(R.id.startGamebtn);
        mHeadlinetext = (TextView) getActivity().findViewById(R.id.headerTxtMainActivity);
        mHeadlinetext.setText(getResources().getString(R.string.menyPlayerVBotText));
        mStartGameBtn.setOnClickListener(this);
        mIntent = new Intent(getActivity(), FrameActivity.class);
        mBuilder = new AlertDialog.Builder(getActivity());
        mAudioPlayer = AudioPlayer.getInstance();
        mStarterPlayerAlert = new StarterPlayerAlert();
        mUserExistAlert = new UserExistAlert();
        mDB = new DBHandler(getActivity().getApplicationContext());
    }

    /**
     * This is a simple method. OnClick method that will handle the when the button start game is
     * pressed.
     * This method will also validate if the name length complies with the rules that we have set.
     */
    @Override
    public void onClick(View v) {

        if (mEnterNameEdittxt.getText().toString().toLowerCase().equals("tttbot")){
            Toast.makeText(getActivity(), "Nice Try! You can't play as the bot", Toast.LENGTH_SHORT).show();
        }
        else if (mEnterNameEdittxt.getText().toString().length() < 8 && !mEnterNameEdittxt.getText().toString().isEmpty()
                && mEnterNameEdittxt.getText().toString().length() > 3) {

            mAudioPlayer.startButtonPressedAudio(getActivity().getApplicationContext(), R.raw.buttonpressed);

            mPlayerOneName = mEnterNameEdittxt.getText().toString().toUpperCase();

            Player player = checkIfUserAlreadyExist(mPlayerOneName);

            if (player == null) addPlayerToDB(mPlayerOneName);


        }else {
            Toast.makeText(getActivity(), "Player name is either long, short or empty, Please try again", Toast.LENGTH_SHORT).show();
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
        settingUpTheGame(player);
    }

    /**
     * This player will simply check if the name the user input is already existing in the Database.
     * If the player already exist in the Database. An Alert will be displayed letting the user know
     * that this is in a existing player. And letting the user know of the option of playing with
     * the existing player.
     *
     * @param playername
     * @return - Player object that has been retrieved from the DB.
     */
    @Override
    public Player checkIfUserAlreadyExist(String playername) {
        Player player =  mDB.getPlayer(playername);

        if ( player != null && !mTempName.equals(playername)){
            mUserExistAlert.settingUpTheCustomAlertDialog(getActivity(), R.layout.play_last_player_alertdialog, mBuilder);
            mTempName = player.getPlayerName();
        }
        else if (mTempName.equals(playername)){
            settingUpTheGame(player);
        }

        return player;
    }

    /**
     * This method will start setting up the game for the user. You will first have the option of
     * picking a player starter. After this is done that the Tic Tac Toe game starts.
     * Methods will also send over both of the player objects.
     * @param player
     */

    private void settingUpTheGame(Player player){

        //Sends the name and what game mode it is using Intent to the GameFragment.
        mIntent.putExtra("FragKey", "GameFragment");
        mIntent.putExtra("playerOne", player);
        mIntent.putExtra("playerTwo", mDB.getPlayer("TTTBOT"));
        mIntent.putExtra("Mode", "Player One Mode");

        //Starts up a custom alert Dialog where the user has the option of picking the starter of the game.
        mStarterPlayerAlert.settingUpTheCustomAlertDialog(R.layout.pick_starter_alertdialog, mBuilder, mIntent, getActivity(),
                player.getPlayerName(), getResources().getString(R.string.player_botsname));
        mEnterNameEdittxt.setText("");
    }
}
