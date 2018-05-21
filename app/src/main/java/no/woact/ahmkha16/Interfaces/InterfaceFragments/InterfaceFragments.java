package no.woact.ahmkha16.Interfaces.InterfaceFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Khalid B. Said on 3/19/2018.
 *
 * A simple Interface class that allows us to create and setUp a fragment in a more
 * easier and efficient way. The respective classes that are presenting a fragment uses
 * this Interface class.
 */

public interface InterfaceFragments {

    void setUpFragments(int idResources, Fragment fragmentObject, FragmentTransaction fragmentTransaction);

}
