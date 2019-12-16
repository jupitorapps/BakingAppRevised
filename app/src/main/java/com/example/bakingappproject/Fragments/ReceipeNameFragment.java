package com.example.bakingappproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingappproject.Utils.ApiClient;
import com.example.bakingappproject.Utils.ApiService;
import com.example.bakingappproject.DataModels.BakingReceipeDataModel;
import com.example.bakingappproject.DataModels.IngrediendsDataModel;
import com.example.bakingappproject.DataModels.ReceipeAdapterClickListener;
import com.example.bakingappproject.DataModels.StepsDataModel;
import com.example.bakingappproject.R;
import com.example.bakingappproject.ReceipeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceipeNameFragment extends Fragment implements ReceipeAdapterClickListener {

    private String TAG = "TAGG";
    private ArrayList<BakingReceipeDataModel> bakingReceipeArrayList;

    private ReceipeAdapterClickListener receipeAdapterClickListener;


    @BindView(R.id.rv_receipenames)
    RecyclerView recyclerViewReceipeNames;

    public ReceipeNameFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        bakingReceipeArrayList = new ArrayList<>();
        ArrayList<IngrediendsDataModel> ingredientsArrayList = new ArrayList<>();
        ArrayList<StepsDataModel> stepsArrayList = new ArrayList<>();

        getReceipeDataFromServer();
        if (getActivity() != null) {
            getActivity().setTitle("Baking Receipe App");
        }

        return rootView;

    }


    private void getReceipeDataFromServer() {

        ApiService service = ApiClient.getRetrofit().create(ApiService.class);
        Call<ArrayList<BakingReceipeDataModel>> call = service.getBakingReceipeData();
        call.enqueue(new Callback<ArrayList<BakingReceipeDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BakingReceipeDataModel>> call, Response<ArrayList<BakingReceipeDataModel>> response) {
                // Log.d(TAG, "onResponse: "+response.body());

                bakingReceipeArrayList = response.body();
                setupUi(bakingReceipeArrayList);


            }

            @Override
            public void onFailure(Call<ArrayList<BakingReceipeDataModel>> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t);

            }
        });

    }


    private void setupUi(ArrayList<BakingReceipeDataModel> bakingReceipeArrayList) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewReceipeNames.setLayoutManager(linearLayoutManager);
        ReceipeAdapter receipeAdapter = new ReceipeAdapter(this);
        recyclerViewReceipeNames.setAdapter(receipeAdapter);
        recyclerViewReceipeNames.hasFixedSize();
        receipeAdapter.loadData(bakingReceipeArrayList);

    }

    @Override
    public void onItemClicks(BakingReceipeDataModel bakingReceipeDataModel) {

        Log.d(TAG, "onItemClicks: " + bakingReceipeDataModel.getName());

        receipeAdapterClickListener.onItemClicks(bakingReceipeDataModel);

    }

    @Override
    public void onStepItemClickListener(  int position, ArrayList<StepsDataModel> stepsDataModelArrayList) {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof ReceipeAdapterClickListener) {
            receipeAdapterClickListener = (ReceipeAdapterClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }

    }
}
