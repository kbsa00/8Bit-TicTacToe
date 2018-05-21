package no.woact.ahmkha16.Fragments.FragmentOptions;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import no.woact.ahmkha16.Interfaces.InterfaceFragments.InterfaceFragments;
import no.woact.ahmkha16.Media.AudioPlayer;
import no.woact.ahmkha16.R;


/**
 * Decription of the class: This fragment Class "GameModeOptions" will present the user their options of what
 * game mode they want to play on this Application.
 * After chosing a "game mode" then application will procede to show their respective fragments.
 *
 */
public class GameModeOptions extends Fragment implements View.OnClickListener, InterfaceFragments {

    //Fields for the class
    private TextView mHeadlineTxt;
    private Button mPlayerVBotButton;
    private Button mPlayerVplayerButton;
    private OnePlayerMode mOnePlayerMode;
    private TwoPlayerMode mTwoPlayerMode;
    private FragmentTransaction mFragmentTransaction;
    private AudioPlayer mAudioPlayer;


    public GameModeOptions() {
        mOnePlayerMode = new OnePlayerMode();
        mTwoPlayerMode = new TwoPlayerMode();
        mAudioPlayer = AudioPlayer.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_mode_options, container, false);
        initialization(view);
        return view;
    }

    /**
     * A simple initialization method that will initialize every widget component the fragment
     * class will need.
     * @param view
     */
    public void initialization(View view){
        mHeadlineTxt = getActivity().findViewById(R.id.headerTxtMainActivity);
        mPlayerVBotButton = view.findViewById(R.id.playervbotbtn);
        mPlayerVplayerButton = view.findViewById(R.id.playervplayerBtn);
        mHeadlineTxt.setText(getResources().getString(R.string.menyGameModeText));
        mPlayerVplayerButton.setOnClickListener(this);
        mPlayerVBotButton.setOnClickListener(this);
    }

    /**
     * This is a simple method. OnClick method that will handle everybutton that is pressed,
     * and will perform their tasks. The buttons are everything from,
     * - Playing a game against the BOT
     * - Playing a game against a friend
     *
     * @param v - The buttons view when pressed on.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.playervbotbtn:
                mAudioPlayer.startButtonPressedAudio(getActivity().getApplicationContext(), R.raw.buttonpressed);
                setUpFragments(R.id.fragContainer, mOnePlayerMode, mFragmentTransaction);
                break;

            case R.id.playervplayerBtn:
                mAudioPlayer.startButtonPressedAudio(getActivity().getApplicationContext(), R.raw.buttonpressed);
                setUpFragments(R.id.fragContainer, mTwoPlayerMode, mFragmentTransaction);
                break;
        }

    }

    /**
     * Simple setUpFragment method that will start a fragment and display it on
     * the Activity. Notice that this method is implemented from InterFaceFragments interface class.
     * @param idResources - Layout Resource
     * @param fragmentObject - Fragment Object
     * @param fragmentTransaction - FragmentTransaction.
     */
    @Override
    public void setUpFragments(int idResources, Fragment fragmentObject, FragmentTransaction fragmentTransaction) {
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(idResources, fragmentObject, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
