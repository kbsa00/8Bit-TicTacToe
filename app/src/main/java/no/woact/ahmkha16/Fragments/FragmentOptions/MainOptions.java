package no.woact.ahmkha16.Fragments.FragmentOptions;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import no.woact.ahmkha16.Activities.FrameActivity;
import no.woact.ahmkha16.Interfaces.InterfaceFragments.InterfaceFragments;
import no.woact.ahmkha16.Media.AudioPlayer;
import no.woact.ahmkha16.R;

/**
 * Description of the Class:  This Fragment Class is for the Main Options that the user will meet
 * when the application starts.
 * This class will present the user the fundemental options of the application.
 *
 * And these are everything from Playing a game, to checking out the Highscore and Introduction.
 *
 * These options or buttons as you may call it will then lead the user to their respective
 * fragments.
 */

public class MainOptions extends Fragment implements View.OnClickListener, InterfaceFragments {

    private Button mPlayButton;
    private Button mHighScoreButton;
    private Button mIntroductionButton;
    private TextView mHeadlineText;

    private GameModeOptions mGameModeOptions;
    private FragmentTransaction mFragmentTransaction;
    private AudioPlayer mAudioPlayer;
    private Intent mIntent;

    public MainOptions() {
        //Creates an object of the fragment class we want to setup.
        mGameModeOptions = new GameModeOptions();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_options, container, false);
        initilization(view);
        return view;
    }

    /**
     * Initilize everything from widgets that are going to be used in this fragment.
     * And also sets their respective values if needed.
     */
    public void initilization(View view){

        mPlayButton = (Button) view.findViewById(R.id.playBtn);
        mHighScoreButton = (Button) view.findViewById(R.id.highScoreBtn);
        mIntroductionButton = (Button) view.findViewById(R.id.introBtn);
        mHeadlineText = (TextView) getActivity().findViewById(R.id.headerTxtMainActivity);

        mHeadlineText.setText(getResources().getString(R.string.menyMainText));
        mPlayButton.setOnClickListener(this);
        mHighScoreButton.setOnClickListener(this);
        mIntroductionButton.setOnClickListener(this);
        mAudioPlayer = AudioPlayer.getInstance();
    }

    /**
     * This is a simple method. OnClick method that will handle everybutton that is pressed,
     * and will perform their tasks. The buttons are everything from,
     * - Playing a game
     * - Looking at the HighScore
     * - get a Introduction.
     *
     * @param v - The buttons view when pressed on.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.playBtn:
                //Sets up the GameMode fragment where the user can choose what mode they want to play.
                mAudioPlayer.startButtonPressedAudio(getActivity().getApplicationContext(), R.raw.buttonpressed);
                setUpFragments(R.id.fragContainer, mGameModeOptions, mFragmentTransaction);
                break;

            case R.id.highScoreBtn:
                mAudioPlayer.startButtonPressedAudio(getActivity().getApplicationContext(), R.raw.buttonpressed);
                 mIntent = new Intent(getActivity().getApplicationContext(), FrameActivity.class);
                 mIntent.putExtra("FragKey", "HighScore");
                startActivity(mIntent);
                break;

            case R.id.introBtn:
                mAudioPlayer.startButtonPressedAudio(getActivity().getApplicationContext(), R.raw.buttonpressed);
                mIntent = new Intent(getActivity().getApplicationContext(), FrameActivity.class);
                mIntent.putExtra("FragKey", "Introduction");
                startActivity(mIntent);
                break;
        }
    }

    /**
     * Simple setUpFragment method that will start a fragment and display it on
     * the Activity. Notice that this method is implemented from InterFaceFragments interface class.
     *
     * @param idResources - Layout Resource
     * @param fragmentObject - Fragment Object
     * @param fragmentTransaction - FragmentTransaction.
     */
    @Override
    public void setUpFragments(int idResources, Fragment fragmentObject, FragmentTransaction fragmentTransaction) {
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(idResources, fragmentObject,null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
