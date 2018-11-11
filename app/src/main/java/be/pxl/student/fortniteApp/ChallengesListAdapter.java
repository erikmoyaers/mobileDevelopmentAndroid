package be.pxl.student.fortniteApp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import be.pxl.student.fortniteApp.dataclasses.Challenge;

import static android.support.v4.content.ContextCompat.startActivity;
import static be.pxl.student.fortniteApp.R.*;

public class ChallengesListAdapter extends RecyclerView.Adapter<ChallengesListAdapter.MyViewHolder> {

    public static Challenge[] mDataset;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mChallengeText;

        public MyViewHolder(View v) {
            super(v);
            mChallengeText = (TextView) v.findViewById(id.textViewChallenge);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO Start new activity with fragment, or in landscape show details in fragment
                    // use the getAdapterPosition() function to pass the id
                    ViewGroup row = (ViewGroup) v.getParent();
                    for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
                        View view = row.getChildAt(itemPos);
                        view.setBackgroundColor(Color.GRAY);
                    }
                    v.setBackgroundColor(Color.BLACK);
                    int orientation = v.getResources().getConfiguration().orientation;
                    TextView itemDetail = ((ViewGroup) row.getParent()).getChildAt(1).findViewById(id.challengeDetail);
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        // In landscape
                        itemDetail.setVisibility(View.VISIBLE);
                        itemDetail.setText(mDataset[getAdapterPosition()].getQuestReward());
                    } else {
                        // In portrait
                       itemDetail.setVisibility(View.INVISIBLE);
                       Class destinationActivity = DetailActivity.class;
                       Intent startChildActivityIntent = new Intent(itemDetail.getContext(),destinationActivity);
                       startChildActivityIntent.putExtra(Intent.EXTRA_TEXT,mDataset[getAdapterPosition()].getQuestReward());
                       startActivity(itemDetail.getContext(),startChildActivityIntent,null);
                    }
                }
            });
        }
    }

    public ChallengesListAdapter(List<Challenge> myDataset) {
        mDataset = myDataset.stream().toArray(Challenge[]::new);
    }

    @Override
    public ChallengesListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.challenges_list_item, parent, false);

        return new MyViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mChallengeText.setText(mDataset[position].getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
