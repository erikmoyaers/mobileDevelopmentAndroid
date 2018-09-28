package com.example.brent.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

//    private int count=0;
//    @BindView(R.id.myButton) Button button;
//    @BindView(R.id.myTextView) TextView textview;
    @BindView(R.id.usernameInput) EditText usernameInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        String username = sharedPref.getString(("username"), defaultValue);

        usernameInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    saveUsername();
                    return true;
                }
                return false;
            }
        });

        if(username!=""){
            //TODO open new Activity or Fragment
            Toast.makeText(getApplicationContext(),
                    username,
                    Toast.LENGTH_SHORT).show();

        }

    }


    public void saveUsername(){
        String username = usernameInput.getText().toString();
        Toast toast = Toast.makeText(getApplicationContext(),
                username,
                Toast.LENGTH_SHORT);

        toast.show();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username",username);
        editor.commit();

    }
}
