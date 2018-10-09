package be.pxl.student.fortniteApp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import be.pxl.student.fortniteApp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class UserStatistics extends Fragment {

    public UserStatistics() {
        // Required empty public constructor
    }


    @BindView(R.id.totalWinsValue)
    TextView totalWins;
    private Unbinder unbinder;
    private String url = "GET https://api.fortnitetracker.com/v1/profile/pc/" + R.id.usernameInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_statistics, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    totalWins.setText("Response: " + response.toString().substring(0,30));
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    System.out.println(error.getMessage());
                }
            });

}
