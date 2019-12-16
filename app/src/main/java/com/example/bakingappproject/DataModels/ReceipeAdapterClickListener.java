package com.example.bakingappproject.DataModels;

import java.util.ArrayList;

public interface ReceipeAdapterClickListener {

    void onItemClicks(BakingReceipeDataModel bakingReceipeDataModel);

    void onStepItemClickListener(int position, ArrayList<StepsDataModel> stepsDataModelArrayList);

}
