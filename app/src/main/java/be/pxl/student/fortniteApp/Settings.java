package be.pxl.student.fortniteApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import be.pxl.student.fortniteApp.LoginFragment;

import be.pxl.student.fortniteApp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Settings extends Fragment {
    @BindView(R.id.editTextChangeUsernameSettings)
    EditText usernameInput;
    @BindView(R.id.platform_spinner_edit)
    Spinner spinner;
    private SharedPreferences sharedPref;

    private Unbinder unbinder;

    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);

        sharedPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        String username = sharedPref.getString(getString(R.string.username), defaultValue);
        usernameInput.setText(username);
        String selectedPlatform = sharedPref.getString("Platform",defaultValue );
        if (selectedPlatform!=""){
            spinner.setSelection(Arrays.asList(getResources().getStringArray(R.array.platforms_array)).indexOf(selectedPlatform));
        }

        savePlatformSettings(spinner);
        saveUsername(usernameInput);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void savePlatformSettings(Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.platform),spinner.getSelectedItem().toString());
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

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.username),username);
                editor.apply();
                //Set username as header title in nav
                TextView headerUsername = getActivity().findViewById(R.id.navText);
                headerUsername.setText(username);

                Toast.makeText(this.getActivity().getApplicationContext(),
                        "username saved...",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });



    }
}
