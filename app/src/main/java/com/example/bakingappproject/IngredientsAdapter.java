package com.example.bakingappproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingappproject.DataModels.IngrediendsDataModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private final ArrayList<IngrediendsDataModel> ingrediendsDataModelArrayList = new ArrayList<>();
    private String TAG = "TAGG";


    public void loadData(ArrayList<IngrediendsDataModel> ingrediendsDataModels) {
        if (ingrediendsDataModelArrayList.size() > 0) {
            this.ingrediendsDataModelArrayList.clear();
            notifyDataSetChanged();
        }

        this.ingrediendsDataModelArrayList.addAll(ingrediendsDataModels);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_ingredients_items_layout, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {

        IngrediendsDataModel currentIngredients = ingrediendsDataModelArrayList.get(position);
        holder.tv_ingredients_name.setText(currentIngredients.getIngredient());
        holder.tv_ingredients_quantity.setText(String.valueOf(currentIngredients.getQuantity()));
        holder.tv_ingredients_measure.setText(currentIngredients.getMeasure().toLowerCase());

    }

    @Override
    public int getItemCount() {
        return ingrediendsDataModelArrayList.size();
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ingredients_name)
        TextView tv_ingredients_name;
        @BindView(R.id.tv_ingredients_quantity)
        TextView tv_ingredients_quantity;
        @BindView(R.id.tv_ingredients_measure)
        TextView tv_ingredients_measure;

        IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

}
