package com.example.bakingappproject.Fragments;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingappproject.BakingAppWidgetProvider;
import com.example.bakingappproject.DataModels.BakingReceipeDataModel;
import com.example.bakingappproject.DataModels.IngrediendsDataModel;
import com.example.bakingappproject.DataModels.ReceipeAdapterClickListener;
import com.example.bakingappproject.DataModels.StepsDataModel;
import com.example.bakingappproject.IngredientsAdapter;
import com.example.bakingappproject.MainActivity;
import com.example.bakingappproject.R;
import com.example.bakingappproject.StepsAdapter;
import com.example.bakingappproject.VideoPlayerActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReceipeDetailsFragment extends Fragment implements ReceipeAdapterClickListener {

    private String TAG = "TAGG";

    //  private ArrayList<IngrediendsDataModel> ingrediendsDataModelArrayList;
    //  private ArrayList<StepsDataModel> stepsDataModelArrayList;

    private ReceipeAdapterClickListener receipeAdapterClickListener;
    static final String SHARED_PREFS = "prefs";

    @BindView(R.id.rv_ingredients)
    RecyclerView ingredientsRecyclerView;

    @BindView(R.id.rv_steps)
    RecyclerView stepsRecyclerView;


    public ReceipeDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_receipe_details, container, false);

        BakingReceipeDataModel bakingReceipeDataModel = null;

        if (getArguments() != null) {

            bakingReceipeDataModel = getArguments().getParcelable("recipe_data");

            Log.d(TAG, "onCreateView: Data In Fragment: "+bakingReceipeDataModel.getIngredients().get(0).getIngredient());

            ButterKnife.bind(this, rootView);

            if (getActivity() != null) {
                getActivity().setTitle(bakingReceipeDataModel.getName());
            }

            if (bakingReceipeDataModel != null) {
                setupIngredientsList(bakingReceipeDataModel.getIngredients());
                setupStepsList(bakingReceipeDataModel.getSteps());

                saveReceipeNameForWidgetHeading(bakingReceipeDataModel.getName());
            }

            //update widget recipe name
            Intent intent = new Intent(getContext(), BakingAppWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            int[] ids = AppWidgetManager.getInstance(getActivity().getApplication())
                    .getAppWidgetIds(new ComponentName(getActivity().getApplication(), BakingAppWidgetProvider.class));
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            getActivity().sendBroadcast(intent);

//for updating widget listview
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
            appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.widget_list_view);

        }

        return rootView;
    }



    private void saveReceipeNameForWidgetHeading(String receipeName) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Editor editor = sharedPreferences.edit();
        editor.putString("receipe_name", receipeName);
        editor.apply();

    }

    private void setupIngredientsList(ArrayList<IngrediendsDataModel> ingrediendsDataModel) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter();
        ingredientsRecyclerView.hasFixedSize();
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        ingredientsAdapter.loadData(ingrediendsDataModel);

        saveIngredientsListToSharedPreference(getContext(), ingrediendsDataModel);

    }

    private void saveIngredientsListToSharedPreference(Context context, ArrayList<IngrediendsDataModel> ingrediendsDataModelObject) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ingrediendsDataModelObject);
        editor.putString("IngredientsSavedList", json);
        editor.apply();

    }

    private void setupStepsList(ArrayList<StepsDataModel> stepsDataModel) {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stepsRecyclerView.setLayoutManager(linearLayoutManager);
        StepsAdapter stepsAdapter = new StepsAdapter(this);
        stepsRecyclerView.hasFixedSize();
        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsAdapter.loadData(stepsDataModel);
    }


    @Override
    public void onItemClicks(BakingReceipeDataModel bakingReceipeDataModel) {
        Log.d(TAG, "onItemClicks: " + bakingReceipeDataModel.getIngredients());

    }

    @Override
    public void onStepItemClickListener(StepsDataModel stepsDataModel) {

        String videoUrl = stepsDataModel.getVideoURL();
        if (videoUrl == null || videoUrl.isEmpty()) {
            Log.d(TAG, "onStepItemClickListener: Video is not available");
        } else {
            Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
            intent.putExtra("video_url", videoUrl);
            startActivity(intent);
        }


    }
}
