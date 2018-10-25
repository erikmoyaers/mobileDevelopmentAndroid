package be.pxl.student.fortniteApp;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.pxl.student.fortniteApp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class Challenge_Detail extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.challengeDetail)
    private TextView challengeDetailText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge__detail, container, false);
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
