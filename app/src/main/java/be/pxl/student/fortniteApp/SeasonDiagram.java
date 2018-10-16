package be.pxl.student.fortniteApp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import be.pxl.student.fortniteApp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SeasonDiagram extends Fragment {

    @BindView(R.id.graphSolo)
    GraphView graphSolo;

    @BindView(R.id.graphDuo)
    GraphView graphDuo;

    @BindView(R.id.graphSquad)
    GraphView graphSquad;

    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
            new DataPoint(0, 1),
            new DataPoint(1, 5),
            new DataPoint(2, 3)
    });


    private Unbinder unbinder;


    public SeasonDiagram() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_season_diagram, container, false);
        unbinder = ButterKnife.bind(this, view);

        graphSolo.addSeries(series);
        graphDuo.addSeries(series);
        graphSquad.addSeries(series);

        return view;
    }

}
