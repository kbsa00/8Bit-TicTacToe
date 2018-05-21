package no.woact.ahmkha16.Game.GameNotifiers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import no.woact.ahmkha16.R;

/**
 * Created by Khalid B. Said on 3/27/2018.
 *
 * This is a simple custom AlertDialog class that was created so that
 * the user has the possibility of actually choosing which player should
 * start when the game starts.
 *
 * The class implements an Interface class called AlertDialogInterface,
 */

public class StarterPlayerAlert {

    //Fields for the class.
    private ImageButton mRedMushroom;
    private ImageButton mGreenMushroom;


    public StarterPlayerAlert(){

    }


    public void settingUpTheCustomAlertDialog(int resources, AlertDialog.Builder builder, Intent intent, Activity activity,
    String playerOneName, String playerTwoName) {

        View view = activity.getLayoutInflater().inflate(resources, null);
         mRedMushroom = (ImageButton) view.findViewById(R.id.redMushroomImageButton);
         mGreenMushroom = (ImageButton) view.findViewById(R.id.greenMushroomImageButton);

        TextView playertextOne = view.findViewById(R.id.player1nametext);
        TextView playerTextTwo = view.findViewById(R.id.player2nametext);

        playertextOne.setText(playerOneName.toUpperCase());
        playerTextTwo.setText(playerTwoName.toUpperCase());

        builder.setCancelable(false);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        mRedMushroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("playerStarter",true);
                activity.startActivity(intent);
                dialog.dismiss();
            }
        });

        mGreenMushroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("playerStarter",false);
                activity.startActivity(intent);
                dialog.dismiss();
            }
        });

    }

}
