package no.woact.ahmkha16.Fragments.FragmentsGame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.woact.ahmkha16.Data.CustomViewAdapter;
import no.woact.ahmkha16.Data.Database.DBHandler;
import no.woact.ahmkha16.R;

/**
 * Created by Khalid B. Said
 * A fragment class for Highscore. This class updates the Recyclerview for us
 * so that we are always showing the user updated information from the Database.
 */
public class GameHighScore extends Fragment {

    //Fields for the class
    private RecyclerView mRecyclerView;
    private CustomViewAdapter mCustomViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public GameHighScore() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_game_high_score, container, false);
        initialization(view);
        setUpHighScore();
        return view;
    }

    /**
     * Simple method for initialization of objects that we need in the class.
     * @param view
     */
    private void initialization(View view){
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        DBHandler dbHandler = new DBHandler(getActivity());
        mCustomViewAdapter = new CustomViewAdapter(dbHandler.getAllPlayers());
    }

    /**
     * This is just a simple method that sets up the recylcerview for us.
     * And always update with the new information from the db.
     */
    private void setUpHighScore() {
        mCustomViewAdapter.notifyDataSetChanged();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCustomViewAdapter);
    }



}
