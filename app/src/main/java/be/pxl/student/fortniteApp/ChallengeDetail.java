package be.pxl.student.fortniteApp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ChallengeDetail extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.challengeDetail)
    public TextView challengeDetailText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge_detail, container, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        if(bundle!=null) {
           challengeDetailText.setText(getArguments().getString("item"));
        }

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
