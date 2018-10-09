package be.pxl.student.fortniteApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import be.pxl.student.fortniteApp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



public class LoginFragment extends Fragment {

    @BindView(R.id.usernameInput)
     EditText usernameInput;
    @BindView(R.id.platform_spinner)
    Spinner spinner;
    private Unbinder unbinder;
    private NavigationView navigationView;



    public LoginFragment() {

    }


    public static LoginFragment newInstance(NavigationView navigationView){
        LoginFragment fragment = new LoginFragment();
        fragment.setNavigationView(navigationView);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
       unbinder = ButterKnife.bind(this, view);

        saveUsername(usernameInput);
        savePlatform(spinner);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void savePlatform(Spinner spinner){
        String platform = spinner.getSelectedItem().toString();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(),
                        spinner.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(String.valueOf(R.string.platform),spinner.getSelectedItem().toString());
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

private void saveUsername(EditText usernameInput){
    usernameInput.setOnKeyListener((v, keyCode, event) -> {
        // If the event is a key-down event on the "enter" button
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            // Perform action on key press
            String username = usernameInput.getText().toString();
            SharedPreferences sharedPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.username),username);
            editor.apply();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            UserStatistics statsFragment = new UserStatistics();
            fragmentTransaction.remove(this);
            fragmentTransaction.add(R.id.mainActivity, statsFragment);
            fragmentTransaction.commit();

            Toast.makeText(this.getActivity().getApplicationContext(),
                    "username saved...",
                    Toast.LENGTH_SHORT).show();
            Menu menuNav = navigationView.getMenu();
            for(int i = 0; i< menuNav.size();i++){
                menuNav.getItem(i).setEnabled(true);
            }
            return true;
        }
        return false;
    });
}

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }
}
