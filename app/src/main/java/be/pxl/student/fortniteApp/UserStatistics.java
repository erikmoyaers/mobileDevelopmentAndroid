package be.pxl.student.fortniteApp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class UserStatistics extends Fragment {

    public UserStatistics() {
        // Required empty public constructor
    }


    @BindView(R.id.totalWinsValue)
    TextView totalWins;
    @BindView(R.id.winPercentageValue)
    TextView winPercentage;
    @BindView(R.id.totalKillsValue)
    TextView totalKills;
    @BindView(R.id.totalScoreValue)
    TextView totalScore;
    @BindView(R.id.totalKDValue)
    TextView totalKd;
    @BindView(R.id.matchesPlayedValue)
    TextView matchesPlayed;

    private Unbinder unbinder;
    HashMap<String,String> userStatsMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_statistics, container, false);
        unbinder = ButterKnife.bind(this, view);
        userStatsMap = MySingleton.getInstance(this.getActivity().getApplicationContext()).sendUserStatsRequest(this.getActivity().getPreferences(Context.MODE_PRIVATE), new ILifetimeStatsCallback() {
            @Override
            public void onSuccesResponse(HashMap<String, String> result) {
                totalWins.setText(result.get("Wins"));
                winPercentage.setText(result.get("Win%"));
                totalKills.setText(result.get("Kills"));
                totalScore.setText(result.get("Score"));
                totalKd.setText(result.get("K/d"));
                matchesPlayed.setText(result.get("Matches Played"));
            }
        });

        Animation fadeIn = new AlphaAnimation(0,1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setDuration(2200);
        view.setAnimation(fadeIn);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
