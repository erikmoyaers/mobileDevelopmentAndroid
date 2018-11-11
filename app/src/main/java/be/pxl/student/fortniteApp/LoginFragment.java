package be.pxl.student.fortniteApp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import be.pxl.student.fortniteApp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;



public class LoginFragment extends Fragment{

    @BindView(R.id.usernameInput)
     EditText usernameInput;
    @BindView(R.id.platform_spinner)
    Spinner spinner;
    @BindView(R.id.imageView)
    ImageView backgroundImage;

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

        Animation fadeIn = new AlphaAnimation(0,1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setDuration(1000);
        view.setAnimation(fadeIn);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.saveButton)
    public void save() {
        savePlatform(spinner);
        saveUsername(usernameInput);
        TextView headerUsername = getActivity().findViewById(R.id.navText);
        headerUsername.setText(usernameInput.getText());
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(usernameInput.getWindowToken(), 0);
    }



    private void savePlatform(Spinner spinner){

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.platform),spinner.getSelectedItem().toString());
        editor.apply();

    }

private void saveUsername(EditText usernameInput){

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
}

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

}
