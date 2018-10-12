package be.pxl.student.fortniteApp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ChallengesListAdapter extends RecyclerView.Adapter<ChallengesListAdapter.MyViewHolder> {

    private String[] mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView mChallengeText;
        public MyViewHolder(View v){
            super(v);
            mChallengeText = (TextView)v.findViewById(R.id.textViewChallenge);
        }
    }

    public ChallengesListAdapter(List<String> myDataset){
        mDataset = myDataset.stream().toArray(String[]::new);
    }

    @Override
    public ChallengesListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.challenges_list_item, parent, false);

        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mChallengeText.setText(mDataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
