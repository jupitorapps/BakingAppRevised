package com.example.bakingappproject;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingappproject.DataModels.IngrediendsDataModel;
import com.example.bakingappproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WidgetService extends RemoteViewsService {
    private static final String TAG = "TAGG";

    private ArrayList<IngrediendsDataModel> ingredients_list = new ArrayList<>();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetItemFactory(getApplicationContext(), intent);
    }


    class WidgetItemFactory implements RemoteViewsFactory {


        private Context context;
        private int appWidgetId;


        WidgetItemFactory(Context context, Intent intent) {
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            //for retriving data

            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(context);
            Gson gson = new Gson();
            String json = appSharedPrefs.getString("IngredientsSavedList", "");

            Type type = new TypeToken<ArrayList<IngrediendsDataModel>>() {
            }.getType();
            ingredients_list = gson.fromJson(json, type);

        }

        @Override
        public void onDataSetChanged() {

            Log.d(TAG, "onDataSetChanged: ");


        }

        @Override
        public void onDestroy() {

            Log.d(TAG, "onDestroy: ");

        }

        @Override
        public int getCount() {
            if (ingredients_list != null) {
                return ingredients_list.size();
            }

            return 0;

        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_listview_items);
            remoteViews.setTextViewText(R.id.widget_list_view_ingredients_name, ingredients_list.get(position).getIngredient());
            remoteViews.setTextViewText(R.id.widget_list_view_ingredients_quantity, String.valueOf(ingredients_list.get(position).getQuantity()));
            remoteViews.setTextViewText(R.id.widget_list_view_ingredients_unit, ingredients_list.get(position).getMeasure());


            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }




    }



}
