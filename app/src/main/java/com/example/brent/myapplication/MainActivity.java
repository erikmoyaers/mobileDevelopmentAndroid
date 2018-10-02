package com.example.brent.myapplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,UserStatistics.OnFragmentInteractionListener,Settings.OnFragmentInteractionListener,Challenges.OnFragmentInteractionListener {


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @BindView(R.id.drawer_layout)
     DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
     NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        String username = sharedPref.getString(getString(R.string.username), defaultValue);


        if(username!=defaultValue){
            //TODO open new Activity or Fragment
            UserStatistics statsFragment = new UserStatistics();
            fragmentTransaction.add(R.id.mainActivity, statsFragment);
            fragmentTransaction.addToBackStack("statsFragment");
            fragmentTransaction.commit();

        }else{

            LoginFragment loginFragment = new LoginFragment();
            fragmentTransaction.add(R.id.mainActivity, loginFragment);
            fragmentTransaction.addToBackStack("loginFragment");
            fragmentTransaction.commit();
        }
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
                    // close drawer when item is tapped
                    mDrawerLayout.closeDrawers();

                    // Add code here to update the UI based on the item selected
                    // For example, swap UI fragments here
                    String menuTitle = menuItem.getTitle().toString();
                    for (Fragment fragment:getSupportFragmentManager().getFragments()) {

                         if (fragment!=null) {
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                    }
                    fragmentTransaction = fragmentManager.beginTransaction();
                    if (getResources().getString(R.string.stats).equals(menuTitle)) {

                        UserStatistics statsFragment = new UserStatistics();

                        fragmentTransaction.add(R.id.mainActivity, statsFragment);
                        fragmentTransaction.addToBackStack("statsFragment");
                        fragmentTransaction.commit();

                    }else if(getResources().getString(R.string.challenges).equals(menuTitle)){

                        Challenges challengesFragment = new Challenges();
                        fragmentTransaction.add(R.id.mainActivity, challengesFragment);
                        fragmentTransaction.addToBackStack("challengesFragment");
                        fragmentTransaction.commit();

                    }else if(getResources().getString(R.string.settings).equals(menuTitle)){
                        Settings settingsFragment = new Settings();
                        fragmentTransaction.add(R.id.mainActivity, settingsFragment);
                        fragmentTransaction.addToBackStack("settingsFragment");
                        fragmentTransaction.commit();
                    }
                    return true;
                });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
