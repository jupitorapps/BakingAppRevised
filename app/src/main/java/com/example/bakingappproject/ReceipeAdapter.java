package com.example.bakingappproject;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bakingappproject.DataModels.BakingReceipeDataModel;
import com.example.bakingappproject.DataModels.ReceipeAdapterClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceipeAdapter extends RecyclerView.Adapter<ReceipeAdapter.ReceipeViewHolder> {

    private final ReceipeAdapterClickListener clickListener;
    private Context context;

    private ArrayList<BakingReceipeDataModel> bakingReceipeDataModelArrayList = new ArrayList<>();

    public ReceipeAdapter(ReceipeAdapterClickListener receipeAdapterClickListener) {
        this.clickListener = receipeAdapterClickListener;
    }

    public void loadData(ArrayList<BakingReceipeDataModel> data) {
        if (bakingReceipeDataModelArrayList.size() > 0) {
            this.bakingReceipeDataModelArrayList.clear();
            notifyDataSetChanged();
        }

        this.bakingReceipeDataModelArrayList.addAll(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ReceipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_items_receipe_names, parent, false);
        return new ReceipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceipeViewHolder holder, int position) {

        BakingReceipeDataModel currentReceipe = bakingReceipeDataModelArrayList.get(position);
        holder.item_receipe_name.setText(currentReceipe.getName());

        if (context != null) {
            Glide.with(context)
                    .load(bakingReceipeDataModelArrayList.get(position).getImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.nutella)
                    .into(holder.item_imgae_view);
        }


    }

    @Override
    public int getItemCount() {
        if (bakingReceipeDataModelArrayList.size() > 0) {
            return bakingReceipeDataModelArrayList.size();
        }
        return 0;
    }


    public class ReceipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_item_receipe_name)
        TextView item_receipe_name;
        @BindView(R.id.item_image_view)
        ImageView item_imgae_view;


        ReceipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            int adapterPosition = getAdapterPosition();
            BakingReceipeDataModel bakingReceipeDataModel = bakingReceipeDataModelArrayList.get(adapterPosition);
            clickListener.onItemClicks(bakingReceipeDataModel);
        }
    }


}
