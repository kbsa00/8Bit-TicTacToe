package no.woact.ahmkha16.Data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import no.woact.ahmkha16.Data.Model.Player;
import no.woact.ahmkha16.R;

/**
 * Created by Khalid B. Said
 * This is a simple custom adapter class that is used for the recyclerView.
 * We proceed to use a viewHolder class to inflate one of our XML classes to
 * input the values on the position, name and wins of each player.
 */

public class CustomViewAdapter extends RecyclerView.Adapter<CustomViewAdapter.MyViewHolder> {
    private ArrayList<Player> mListOfPlayersFromDB;


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nametext, winText, positiontxt;

        public MyViewHolder(View itemView) {
            super(itemView);
            nametext = itemView.findViewById(R.id.highscorenametxt);
            winText = itemView.findViewById(R.id.highscorewintxt);
            positiontxt = itemView.findViewById(R.id.higscorepositiontxt);
        }
    }

    public CustomViewAdapter(ArrayList<Player> listOfPlayersFromDB){
        this.mListOfPlayersFromDB = listOfPlayersFromDB;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_list, parent,
                false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Player player = mListOfPlayersFromDB.get(position);

        holder.nametext.setText(player.getPlayerName());
        holder.winText.setText(String.valueOf(player.getPlayerWins()));
        holder.positiontxt.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return mListOfPlayersFromDB.size();
    }

}
