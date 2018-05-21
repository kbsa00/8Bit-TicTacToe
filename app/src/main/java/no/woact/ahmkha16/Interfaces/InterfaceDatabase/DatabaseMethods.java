package no.woact.ahmkha16.Interfaces.InterfaceDatabase;

import no.woact.ahmkha16.Data.Model.Player;

/**
 * Created by Khalid B. Said
 * This interface class is being used by OnePlayerMode and TwoPlayerMode classes.
 * These two methods are inteded to be used for either add a player to the db or check if a player
 * already exist.
 */

public interface DatabaseMethods {

    void addPlayerToDB(String playerName);

    Player checkIfUserAlreadyExist(String playername);

}
