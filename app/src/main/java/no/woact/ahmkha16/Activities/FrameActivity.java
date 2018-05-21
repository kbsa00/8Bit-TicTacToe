package no.woact.ahmkha16.Activities;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import no.woact.ahmkha16.Fragments.FragmentsGame.GameFragment;
import no.woact.ahmkha16.Fragments.FragmentsGame.GameHighScore;
import no.woact.ahmkha16.Fragments.FragmentsOther.Introduction;
import no.woact.ahmkha16.Interfaces.InterfaceFragments.InterfaceFragments;
import no.woact.ahmkha16.Media.AudioPlayer;
import no.woact.ahmkha16.R;

/**
 * A simple frame Activity class where I primarily use this class for switching
 * three different fragments on it. To be more specific on the fragments that are
 * being displayed in this activity are: Introduction, Highscore and GameFragment.
 */

public class FrameActivity extends AppCompatActivity implements InterfaceFragments{
    private FragmentTransaction mFragmentTransaction;
    private GameFragment mGameFragment;
    private GameHighScore mGameHighScore;
    private Introduction mIntroduction;
    private AudioPlayer mAudioPlayer;
    private int mCurrentGameThemeSongPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        initialization();
        mAudioPlayer = AudioPlayer.getInstance();
        displayFragments();

    }

    /**
     * Simple displayfragments method where I switch on the fragments that are going
     * to be displayed. This is of course based on where you are in the application.
     */
    private void displayFragments() {
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        if (bundle != null){

            switch(bundle.getString("FragKey")){

                case "HighScore":
                    setUpFragments(R.id.fragmentLayout, mGameHighScore, mFragmentTransaction);
                    break;

                case "GameFragment":
                    setUpFragments(R.id.fragmentLayout, mGameFragment, mFragmentTransaction);
                    break;

                case "Introduction":
                    mAudioPlayer.pauseAudio();
                    setUpFragments(R.id.fragmentLayout, mIntroduction, mFragmentTransaction);

                    break;
            }

        }

    }


    /**
     * Simple method for intialization of the fragment objects.
     */
    public void initialization(){
        mGameFragment = new GameFragment();
        mGameHighScore = new GameHighScore();
        mIntroduction = new Introduction();
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
    public void setUpFragments(int idResources, Fragment fragmentObject, android.support.v4.app.FragmentTransaction fragmentTransaction) {

        fragmentTransaction = getSupportFragmentManager().beginTransaction().add(idResources, fragmentObject, null);
        fragmentTransaction.commit();
    }


    /**
     * Using the Activity's life cycle methods for handling how the applications theme song
     * will work while the activity is in their respective states.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mAudioPlayer.pauseAudio();
        mCurrentGameThemeSongPosition = mAudioPlayer.getCurrentSongPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAudioPlayer.resumeAudio(mCurrentGameThemeSongPosition);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAudioPlayer.startAudio(getApplicationContext(), R.raw.battlefield);
    }
}
