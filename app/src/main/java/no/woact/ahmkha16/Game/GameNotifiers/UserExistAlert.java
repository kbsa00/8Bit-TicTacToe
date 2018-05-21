package no.woact.ahmkha16.Game.GameNotifiers;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import no.woact.ahmkha16.R;

/**
 * Created by Khalid B. Said
 *
 * This is just a simple class that proceeds to show the user a small alertdialog
 * if they were trying to play with a existing player from the Database.
 * There's not much to comment for this class since its pretty self-explanatory
 */
public class UserExistAlert {



    public UserExistAlert(){

    }

    public void settingUpTheCustomAlertDialog(Activity activity, int resources, AlertDialog.Builder builder) {

        View view = activity.getLayoutInflater().inflate(resources, null);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        Button okbtn = (Button) view.findViewById(R.id.okbtn);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }



}


