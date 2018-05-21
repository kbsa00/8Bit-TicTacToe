package no.woact.ahmkha16.Game.GameNotifiers;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import no.woact.ahmkha16.Data.CustomViewAdapter;
import no.woact.ahmkha16.Data.Database.DBHandler;
import no.woact.ahmkha16.Data.Model.Player;
import no.woact.ahmkha16.Fragments.FragmentsGame.GameFragment;
import no.woact.ahmkha16.Fragments.FragmentsGame.GameHighScore;
import no.woact.ahmkha16.Interfaces.InterfaceFragments.InterfaceFragments;
import no.woact.ahmkha16.R;

/**
 * Created by Khalid B. Said on 3/27/2018.
 * This class is a simple custom AlertDialog class that pops up
 * after there's a winner or not in the game. Also letting the user decide whether
 * they want to play a new round or check the Highscore.
 *
 */

public class AfterGameAlert implements InterfaceFragments{

    //Fields for the class

    private TextView mHeadlineTxt;
    private Button mHighscoreBtn;
    private Button mRestartGameBtn;
    private Activity mActivity;
    private GameFragment mGameFragment;
    private GameHighScore mGameHighScore;
    private FragmentActivity mContext;
    private FragmentTransaction mFragmentTransaction;


    public AfterGameAlert(Activity mActivity){
        this.mActivity = mActivity;
        mGameFragment = new GameFragment();
        mGameHighScore = new GameHighScore();
    }

    /**
     * Setting up the custom AlertDialog with two options. Restart the game
     * or go on to check the HighScore leaderboards.
     * @param resources - An XML resource of the alert dialog.
     * @param builder - A simple Builder object for the AlertDialog.
     * @param playerWinner
     */
    public void settingUpTheCustomAlertDialog(int resources, AlertDialog.Builder builder,
                                              String playerWinner) {
        View view = mActivity.getLayoutInflater().inflate(resources, null);

        mHeadlineTxt = (TextView) view.findViewById(R.id.afterGameHeaderTxt);
        mHighscoreBtn = (Button) view.findViewById(R.id.afterGameHighscoreBtn);
        mRestartGameBtn = (Button) view.findViewById(R.id.afterGameRestartBtn);

        mHeadlineTxt.setText(playerWinner);

        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        mRestartGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpFragments(R.id.fragmentLayout, mGameFragment, mFragmentTransaction);
                dialog.dismiss();
            }
        });


        mHighscoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpFragments(R.id.fragmentLayout, mGameHighScore, mFragmentTransaction);
                dialog.dismiss();
            }
        });

    }


    public void updatePlayerWinnersScoreToTheDatabase(Player playerWinner){
        DBHandler dbHandler = new DBHandler(mActivity);
        Player player = dbHandler.getPlayer(playerWinner.getPlayerName());
        player.setPlayerWins(player.getPlayerWins()+1);

        dbHandler.updatePlayersScore(player.getPlayerName(), player.getPlayerWins());
    }

    @Override
    public void setUpFragments(int idResources, Fragment fragmentObject, FragmentTransaction fragmentTransaction) {
        mContext = (FragmentActivity) mActivity;
        fragmentTransaction = mContext.getSupportFragmentManager().beginTransaction().replace(idResources, fragmentObject, null);
        fragmentTransaction.commit();

    }
}
