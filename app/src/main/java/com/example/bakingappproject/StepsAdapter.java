package com.example.bakingappproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bakingappproject.DataModels.ReceipeAdapterClickListener;
import com.example.bakingappproject.DataModels.StepsDataModel;
import com.example.bakingappproject.Fragments.ReceipeDetailsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private ArrayList<StepsDataModel> stepsDataModelArrayList = new ArrayList<>();
    private Context context;

    private final ReceipeAdapterClickListener clickListener;

    public StepsAdapter(ReceipeAdapterClickListener receipeAdapterClickListener) {
        this.clickListener = receipeAdapterClickListener;
    }

    public void loadData(ArrayList<StepsDataModel> stepsDataModel) {
        if (stepsDataModelArrayList.size() > 0) {
            stepsDataModelArrayList.clear();
            notifyDataSetChanged();
        }

        stepsDataModelArrayList.addAll(stepsDataModel);
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_steps_items_layout, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {

        StepsDataModel currentStep = stepsDataModelArrayList.get(position);
        holder.tv_steps_description.setText(currentStep.getDescription());
        holder.tv_steps_short_description.setText(currentStep.getShortDescription());

        if (context != null) {
            Glide
                    .with(context)
                    .load(stepsDataModelArrayList.get(position).getThumbnailURL())
                    .centerCrop()
                    .placeholder(R.drawable.ic_video_player)
                    .into(holder.iv_video_thumbnail);
        }


    }

    @Override
    public int getItemCount() {
        return stepsDataModelArrayList.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_step_short_description)
        TextView tv_steps_short_description;
        @BindView(R.id.tv_steps_description)
        TextView tv_steps_description;

        @BindView(R.id.iv_video_thumbnail)
        ImageView iv_video_thumbnail;


        StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            StepsDataModel stepsDataModel = stepsDataModelArrayList.get(adapterPosition);
            clickListener.onStepItemClickListener(stepsDataModel);
        }
    }
}
