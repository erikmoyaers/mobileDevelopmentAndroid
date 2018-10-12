package be.pxl.student.fortniteApp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import be.pxl.student.fortniteApp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class Challenges extends Fragment {


    @BindView(R.id.rv_challenges)
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Unbinder unbinder;

    public Challenges() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<String> challenges = MySingleton.getInstance(this.getActivity().getApplicationContext()).getChallenges();

        mAdapter = new ChallengesListAdapter(challenges);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

}
