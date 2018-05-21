package no.woact.ahmkha16.Data.Model;

import java.io.Serializable;

/**
 * Created by Eier on 3/28/2018.
 */

public class Player implements Serializable{

    private String playerName;
    private int playerWins;

    public Player() {

    }

    public Player(String playerName, int playerWins) {
        this.playerName = playerName;
        this.playerWins = playerWins;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerWins() {
        return playerWins;
    }

    public void setPlayerWins(int playerWins) {
        this.playerWins = playerWins;
    }

}
