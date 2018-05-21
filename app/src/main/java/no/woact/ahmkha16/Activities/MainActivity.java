package no.woact.ahmkha16.Activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import no.woact.ahmkha16.Fragments.FragmentOptions.MainOptions;
import no.woact.ahmkha16.Interfaces.InterfaceFragments.InterfaceFragments;
import no.woact.ahmkha16.Media.AudioPlayer;
import no.woact.ahmkha16.R;


/**
 * Description of the Class: The MainActivity is a simple Activity containing fragments.
 * The Fragments will be switching while activity will still be the same until
 * you launch to a new activity.
 *
 * This Activity will contain everything from being in the main menu all the way
 * to starting the actual game.
 *
 */

public class MainActivity extends AppCompatActivity implements InterfaceFragments{

   // private MainOptions mMainOptions;
    private AudioPlayer mAudioPlayer;
    private FragmentTransaction mFragmentTransaction;
    private int mCurrentMainThemeSongPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creates an object of the fragment class and then sets up the fragment.
        MainOptions mainOptions = new MainOptions();
        setUpFragments(R.id.fragContainer, mainOptions, mFragmentTransaction);

        //Creates a instance of the audioPlayer Class for better handling for playing the App theme song.
        mAudioPlayer = AudioPlayer.getInstance();
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction().add(idResources, fragmentObject,null);
        fragmentTransaction.commit();
    }

    /**
     * Using the Activity's life cycle methods for handling how the applications theme song
     * will work while the activity is in their respective states.
     */
    @Override
    protected void onStart() {
        super.onStart();
        //Starts the appliction's theme song.
        mAudioPlayer.startAudio(getApplicationContext(), R.raw.overworld);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Pauses the applications theme song and also remembers the position the song was in.
        mAudioPlayer.pauseAudio();
        mCurrentMainThemeSongPosition = mAudioPlayer.getCurrentSongPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Resumes the applications theme where it was paused from last time.
        mAudioPlayer.resumeAudio(mCurrentMainThemeSongPosition);
    }


}
