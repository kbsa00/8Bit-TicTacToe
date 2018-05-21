package no.woact.ahmkha16.Media;


import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Khalid B. Said on 3/20/2018.
 * <p>
 * This is a simple AudioPlayer class that is using a Singleton Design pattern.
 * This class is intended to be used for playing of different types of songs/audio
 * through out the application. I found this was easier to use than creating objects of media-player
 * left and right.
 * <p>
 * The methods in this class are pretty self explanatory.
 */
public class AudioPlayer {

    private static AudioPlayer mInstance = null;
    private MediaPlayer mMediaPlayer;
    private MediaPlayer mMedia;

    private AudioPlayer() {
        mMediaPlayer = null;
    }

    /**
     * Get instance audio player.
     *
     * @return the audio player
     */
    public static AudioPlayer getInstance(){
        if (mInstance == null){
            mInstance = new AudioPlayer();
        }
        return mInstance;
    }

    /**
     * Start audio.
     *
     * @param context        the context
     * @param audioResources the audio resources
     */
    public void startAudio(Context context, int audioResources){
        mMediaPlayer = MediaPlayer.create(context, audioResources);
        mMediaPlayer.start();
        mMediaPlayer.setLooping(true);
    }

    /**
     * Stop audio.
     */
    public void stopAudio(){
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }

    /**
     * Pause audio.
     */
    public void pauseAudio(){
        mMediaPlayer.pause();
    }

    /**
     * Resume audio.
     *
     * @param currentSongLength the current song length
     */
    public void resumeAudio(int currentSongLength){
        mMediaPlayer.seekTo(currentSongLength);
        mMediaPlayer.start();
    }


    /**
     * Start button pressed audio.
     *
     * @param context        the context
     * @param audioResources the audio resources
     */
    public void startButtonPressedAudio(Context context, int audioResources){
        mMedia = MediaPlayer.create(context, audioResources);
        mMedia.start();

        mMedia.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mMedia.release();
            }
        });

    }

    /**
     * Get current song position int.
     *
     * @return the int
     */
    public int getCurrentSongPosition(){
        return mMediaPlayer.getCurrentPosition();
    }
}
