package com.udacityscholar.alikhsan778.bakkingapps;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * cus alikhsan778
 */

public class RvStepsAdapter extends RecyclerView.Adapter<RvStepsAdapter.ViewHolder> {

    private ArrayList<StepsObject> stepsObjectArrayList;
    private StepsOnClickListener stepsOnClickListener;

    public RvStepsAdapter(ArrayList<StepsObject> stepsObjectArrayList, StepsOnClickListener stepsOnClickListener) {
        this.stepsObjectArrayList = stepsObjectArrayList;
        this.stepsOnClickListener = stepsOnClickListener;
    }

    @Override
    public RvStepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_steps,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.icPlay.setImageResource(R.drawable.ic_action_play);
        holder.stepTitle.setText(stepsObjectArrayList.get(position).shortDescription);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepsOnClickListener.onStepItemClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return stepsObjectArrayList.size();
    }

    public interface StepsOnClickListener {
        void onStepItemClicked(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_steps_title)
        TextView stepTitle;
        @BindView(R.id.ic_play)
        ImageView icPlay;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
