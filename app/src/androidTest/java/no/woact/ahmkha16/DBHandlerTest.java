package no.woact.ahmkha16;


import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import no.woact.ahmkha16.Data.Database.DBHandler;
import no.woact.ahmkha16.Data.Model.Player;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Created by Khalid B. Said
 *
 * This class is for testing our Database. We try to populate,Update, delete the Database and then
 * proceed to check if they really exist in the DB.
 * We are basically testing all of the CRUD operations against the DB.
 */

public class DBHandlerTest {

    private DBHandler dbHandler;
    private Player player;


    @Before
    public void settingUp(){
        dbHandler = new DBHandler(InstrumentationRegistry.getTargetContext());

    }

    @After
    public void shutDown(){

        dbHandler = null;
    }

    @Test
    public void checkIfDBisNULL(){
        assertNotNull(dbHandler);
    }

    @Test
    public void testIfPlayerNotExists() {

        Player player = dbHandler.getPlayer("nonExistsUser");
        assertNull(player);
    }

    @Test
    public void addingPlayerToDBTest(){
        player = new Player("Tester", 1);
        dbHandler.addPlayer(player);

        Player playerTest = dbHandler.getPlayer("Tester");

        String name = playerTest.getPlayerName();
        int wins = playerTest.getPlayerWins();

        assertEquals("Tester", name);
        assertEquals(1, wins);
        dbHandler.deletePlayerFromDB(player.getPlayerName());
    }

    @Test
    public void updatePlayerStatsTest(){
       player = new Player("Tester", 1);
       dbHandler.addPlayer(player);

       dbHandler.updatePlayersScore(player.getPlayerName(), 2);
       Player playerTest = dbHandler.getPlayer(player.getPlayerName());

       assertEquals("Tester", playerTest.getPlayerName());
       dbHandler.deletePlayerFromDB(player.getPlayerName());
    }

}
