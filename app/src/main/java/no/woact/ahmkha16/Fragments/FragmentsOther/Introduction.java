package no.woact.ahmkha16.Fragments.FragmentsOther;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.json.JSONException;
import org.json.JSONObject;

import no.woact.ahmkha16.API.HTTPHandler;
import no.woact.ahmkha16.API.QTDModel;
import no.woact.ahmkha16.Media.AudioPlayer;
import no.woact.ahmkha16.R;

/**
 * A Introduction fragment class. This fragment class is used for presenting
 * the newest quote of the day and also proceeds to use Youtube's API and showing
 * the user a video of our choice.
 */
public class Introduction extends Fragment {

    //Fields of the class.
    private AudioPlayer mAudioPlayer;
    private TextView mAuthor;
    private TextView mQuote;


    public Introduction() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_introduction, container, false);
        initilization(view);
        setUpYoutube();
        QuoteOfTheDay quoteOfTheDay = new QuoteOfTheDay();
        quoteOfTheDay.execute();
        return view;
    }

    private void initilization(View view) {
        mQuote = (TextView) view.findViewById(R.id.quoteOfTheDaytxt);
        mAuthor = (TextView) view.findViewById(R.id.authorQuotetxt);
    }


    /**
     * This method will set up the youtube video for the user and then immediately start
     * playing this video for the user.
     */
    private void setUpYoutube(){
        YouTubePlayerSupportFragment youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().add(R.id.youtubefragment, youTubePlayerSupportFragment, null);
        fragmentTransaction.commit();

        youTubePlayerSupportFragment.initialize(getActivity().getResources().getString(R.string.API_KEY), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(getActivity().getResources().getString(R.string.Youtube_Video));
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getContext(), "Could not load the Youtube Video.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Simple AsyncTask Class that helps us get the newest Quote of the day from a
     * Rest API.
     */
    private class QuoteOfTheDay extends AsyncTask<Void, Void, QTDModel> {
        private final String URL_QTD = "https://favqs.com/api/qotd";
        private QTDModel qtdModel;

        @Override
        protected QTDModel doInBackground(Void... voids) {
            HTTPHandler httpHandler = new HTTPHandler();
            String JsonString = httpHandler.createHTTPRequest(URL_QTD);

            if (JsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(JsonString).getJSONObject("quote");
                    qtdModel = new QTDModel(jsonObject.getString("author"), jsonObject.getString("body"));
                    return qtdModel;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(QTDModel qtdModel) {
            super.onPostExecute(qtdModel);
            setUpQuoteOfTheDay(qtdModel);
        }
    }

    /**
     * This method will setup and show the user the quote of the day.
     * @param quoteOfTheDay
     */
    private void setUpQuoteOfTheDay(QTDModel quoteOfTheDay){

        if (quoteOfTheDay != null) {
            mQuote.setText(quoteOfTheDay.getQuote());
            mAuthor.setText(quoteOfTheDay.getAuthorName());
        }else {
            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong, When retrieving the Quote of the day", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Pauses the song from previous activity so that the user can watch the video without
     * any app song running in the background.
     */
    @Override
    public void onResume() {
        super.onResume();
        mAudioPlayer = AudioPlayer.getInstance();
        mAudioPlayer.pauseAudio();
    }
}
