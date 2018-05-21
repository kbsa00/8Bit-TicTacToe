package no.woact.ahmkha16.Activities;


import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import no.woact.ahmkha16.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by Eier on 4/18/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTesterRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private String playerOneName = "Pedro";
    private String playerTwoName = "Kokos";


    @Before
    public void setUp() {
    }


    @Test
    public void highScoreFragmentTest(){
        onView(withId(R.id.highScoreBtn)).perform(click());
        onView(withId(R.id.highscoreheadertxt)).check(matches(withText("HIGHSCORES")));
        Espresso.pressBack();
        introductionFragmentTest();
        gameModeTest();
        onePlayerModeTest();
        twoPlayerModeTest();
        inputPlayerOneScenarioTest();
        inputPlayerTwoScenarioTest();
        starterTest();
    }


    public void introductionFragmentTest(){
        onView(withId(R.id.introBtn)).perform(click());
        onView(withId(R.id.introductionheadertxt)).check(matches(withText("INTRODUCTION")));
        Espresso.pressBack();
    }

    public void gameModeTest(){
        onView(withId(R.id.playBtn)).perform(click());
        onView(withId(R.id.headerTxtMainActivity)).check(matches(withText("GAME MODE")));
    }


    public void onePlayerModeTest(){
        onView(withId(R.id.playervbotbtn)).perform(click());
        onView(withId(R.id.headerTxtMainActivity)).check(matches(withText("1 Player Mode")));
        Espresso.pressBack();
    }

    public void twoPlayerModeTest(){
        onView(withId(R.id.playervplayerBtn)).perform(click());
        onView(withId(R.id.headerTxtMainActivity)).check(matches(withText("2 Player Mode")));
    }

    public void inputPlayerOneScenarioTest(){
        onView(withId(R.id.playerheadtxt)).check(matches(withText("PLAYER ONE")));
        onView(withId(R.id.enternametext)).perform(typeText(playerOneName));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.start1v1gameBtn)).perform(click());

    }

    public void inputPlayerTwoScenarioTest(){
        onView(withId(R.id.playerheadtxt)).check(matches(withText("PLAYER TWO")));
        onView(withId(R.id.enternametext)).perform(typeText(playerTwoName));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.start1v1gameBtn)).perform(click());
    }

    public void starterTest(){
        onView(withId(R.id.headerPickStarterText)).check(matches(withText("PICK THE STARTER")));
        onView(withId(R.id.redMushroomImageButton)).perform(click());
    }

    @After
    public void tearDown() throws Exception {

    }

}
