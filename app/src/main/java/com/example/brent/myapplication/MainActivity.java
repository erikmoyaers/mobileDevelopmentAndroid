package com.example.brent.myapplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,UserStatistics.OnFragmentInteractionListener {


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
