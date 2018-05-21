package no.woact.ahmkha16.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import no.woact.ahmkha16.Data.Database.Config.DBConfig;
import no.woact.ahmkha16.Data.Database.DBHandler;
import no.woact.ahmkha16.Data.Model.Player;
import no.woact.ahmkha16.Media.AudioPlayer;
import no.woact.ahmkha16.R;

/**
 * Description of the class:
 * The IntroActivity Class will present a splash intro with animation for the user that will be
 * presented only for 3 seconds before going on the MainActivity.
 */
public class IntroActivity extends AppCompatActivity  {

    //Fields for this class
    private TextView mApplicationNametxt;
    private ImageView mApplicationLogo;
    private Handler mHandler;
    private  MediaPlayer mMediaPlayer;
    private AudioPlayer mAudioPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mAudioPlayer = AudioPlayer.getInstance();


        intilizeWidgets();

        //Animation for the picture starts
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeanim);
        mApplicationLogo.startAnimation(animation);
        mApplicationNametxt.startAnimation(animation);

        //Intro music starts for the splash intro activity.
        mAudioPlayer.startButtonPressedAudio(getApplicationContext(), R.raw.introaudio);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //After the animation is done the activity will then jump over to MainActivity.
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }

    /**
     * This method will initilize all of the widgets and also create objects that
     * it needs for this class to handle and work with
     */
    public void intilizeWidgets(){
        mApplicationLogo = (ImageView) findViewById(R.id.appLogo);
        mApplicationNametxt = (TextView) findViewById(R.id.appNameTxt);
        mHandler = new Handler();

    }
}

