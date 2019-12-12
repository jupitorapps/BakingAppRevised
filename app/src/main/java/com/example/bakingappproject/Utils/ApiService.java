package com.example.bakingappproject.Utils;

import com.example.bakingappproject.DataModels.BakingReceipeDataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("baking.json")
    Call<ArrayList<BakingReceipeDataModel>> getBakingReceipeData();

}
