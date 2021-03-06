package be.pxl.student.fortniteApp;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @BindView(R.id.drawer_layout)
     DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
     NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        requestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

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
                        fragmentTransaction.commit();

                    }else if(getResources().getString(R.string.challenges).equals(menuTitle)){

                        Challenges challengesFragment = new Challenges();
                        fragmentTransaction.add(R.id.mainActivity, challengesFragment);
                        fragmentTransaction.commit();

                    }else if(getResources().getString(R.string.settings).equals(menuTitle)){
                        Settings settingsFragment = new Settings();
                        fragmentTransaction.add(R.id.mainActivity, settingsFragment);
                        fragmentTransaction.commit();
                    }
                    else if(getResources().getString(R.string.comparison).equals(menuTitle)){
                        SeasonDiagram seasonDiagramFragment = new SeasonDiagram();
                        fragmentTransaction.add(R.id.mainActivity, seasonDiagramFragment);
                        fragmentTransaction.commit();
                    }
                    return true;
                });


        if (savedInstanceState != null) {
            return;
        }



        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        String username = sharedPref.getString(getString(R.string.username), defaultValue);


        if(!username.equals(defaultValue)){
            TextView headerUsername = navigationView.getHeaderView(0).findViewById(R.id.navText);
            headerUsername.setText(username);
            UserStatistics statsFragment = new UserStatistics();
            navigationView.getMenu().getItem(1).setChecked(true);
            fragmentTransaction.add(R.id.mainActivity, statsFragment);
            fragmentTransaction.commit();

        }else{
            LoginFragment loginFragment = LoginFragment.newInstance(navigationView);
            fragmentTransaction.add(R.id.mainActivity, loginFragment);
            fragmentTransaction.commit();
            Menu menuNav = navigationView.getMenu();
            for(int i = 0; i< menuNav.size();i++){
                menuNav.getItem(i).setEnabled(false);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
