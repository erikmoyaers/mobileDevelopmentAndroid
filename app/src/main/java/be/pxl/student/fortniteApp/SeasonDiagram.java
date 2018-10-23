package be.pxl.student.fortniteApp;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.HashMap;

import be.pxl.student.fortniteApp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SeasonDiagram extends Fragment {

    @BindView(R.id.graphKills)
    GraphView graphKills;

    @BindView(R.id.graphWins)
    GraphView graphWins;

    @BindView(R.id.graphKd)
    GraphView graphKd;



    private HashMap<String, String> userStatsMap;


    private Unbinder unbinder;
    private int wins;
    private HashMap<String,String> tfueStats;
    private HashMap<String,String> ninjaStats;


    public SeasonDiagram() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_season_diagram, container, false);
        unbinder = ButterKnife.bind(this, view);
        tfueStats = MySingleton.getInstance(this.getContext()).getStatsTfue();
        ninjaStats = MySingleton.getInstance(this.getContext()).getStatsNinja();

        userStatsMap = MySingleton.getInstance(this.getActivity().getApplicationContext()).sendUserStatsRequest(this.getActivity().getPreferences(Context.MODE_PRIVATE), new ILifetimeStatsCallback() {
            @Override
            public void onSuccesResponse(HashMap<String, String> result) {
                int killsTfue = Integer.parseInt(tfueStats.get("Kills"));
                int killsNinja = Integer.parseInt(ninjaStats.get("Kills"));

                int winsTfue = Integer.parseInt(tfueStats.get("Wins"));
                int winsNinja = Integer.parseInt(ninjaStats.get("Wins"));

                double kdTfue = Double.parseDouble(tfueStats.get("K/d"));
                double kdNinja = Double.parseDouble(ninjaStats.get("K/d"));


                BarGraphSeries<DataPoint> seriesKills = new BarGraphSeries<>(new DataPoint[] {
                        new DataPoint(1, killsTfue),
                        new DataPoint(2, killsNinja),
                        new DataPoint(3, Integer.parseInt(userStatsMap.get("Kills")))
                        
                });

                BarGraphSeries<DataPoint> seriesWins = new BarGraphSeries<>(new DataPoint[] {
                        new DataPoint(1, winsTfue),
                        new DataPoint(2, winsNinja),
                        new DataPoint(3, Integer.parseInt(userStatsMap.get("Wins")))

                });

                BarGraphSeries<DataPoint> seriesKd = new BarGraphSeries<>(new DataPoint[] {
                        new DataPoint(1, kdTfue),
                        new DataPoint(2, kdNinja),
                        new DataPoint(3, Double.parseDouble(userStatsMap.get("K/d")))

                });

                seriesKills.setSpacing(50);
                seriesKills.setDrawValuesOnTop(true);
                seriesKills.setValuesOnTopColor(Color.RED);

                seriesWins.setSpacing(50);
                seriesWins.setDrawValuesOnTop(true);
                seriesWins.setValuesOnTopColor(Color.RED);

                seriesKd.setSpacing(50);
                seriesKd.setDrawValuesOnTop(true);
                seriesKd.setValuesOnTopColor(Color.RED);


                graphKills.addSeries(seriesKills);
                graphWins.addSeries(seriesWins);
                graphKd.addSeries(seriesKd);
            }
        });







        return view;
    }

}
