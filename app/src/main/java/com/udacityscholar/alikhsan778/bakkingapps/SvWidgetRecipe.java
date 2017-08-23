package com.udacityscholar.alikhsan778.bakkingapps;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * alikhsan778
 * cus
 */

public class SvWidgetRecipe extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewFactory(this.getApplicationContext(),intent);
    }

    private class RecipeRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory{

        private Context context;
        private ArrayList<RecipeObject> recipeObjectArrayList;
        private ArrayList<IngredientObject> ingredientObjectArrayList = new ArrayList<IngredientObject>();
        private AppWidgetManager appWidgetManager;
        private int appWidgetId;

        public RecipeRemoteViewFactory(Context context , Intent intent){
            recipeObjectArrayList = new ArrayList<>();
            this.context = context;
            appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        @Override
        public void onCreate() {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            if (isNetworkAvailable()){
                String url = getResources().getString(R.string.url);
                JsonArrayRequest jsonArrayRequest = getJsonArray(url);
                requestQueue.add(jsonArrayRequest);
            }
             }

        private JsonArrayRequest getJsonArray(String url) {
            return new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    recipeObjectArrayList = parseRecipeJson(response);
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_stackView);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }

        private ArrayList<RecipeObject> parseRecipeJson(JSONArray response) {
            ArrayList<RecipeObject> returnedRecipeList= new ArrayList<>();

            if (response !=null){
                try{
                    int recipeId;
                    String name;
                    ArrayList<IngredientObject> ingredients;
                    ArrayList<StepsObject> steps;
                    int servings;

                    for (int l=0;l<response.length();l++){
                        JSONObject obj = response.getJSONObject(l);
                        recipeId = obj.getInt("id");
                        name = obj.getString("name");
                        ingredients = new ArrayList<>();
                        steps = new ArrayList<>();
                        JSONArray ingredientsJsonArray = obj.getJSONArray("ingredients");
                        JSONArray stepsJsonArray = obj.getJSONArray("steps");
                        servings = obj.getInt("servings");

                        //Creating Arraylist out of JSONArray
                        for (int x=0;x<ingredientsJsonArray.length();x++){
                            JSONObject ingredientsObject = ingredientsJsonArray.getJSONObject(x);
                            double quantity = ingredientsObject.getDouble("quantity");
                            String measure = ingredientsObject.getString("measure");
                            String ingredient = ingredientsObject.getString("ingredient");
                            ingredients.add(new IngredientObject(quantity,measure,ingredient));
                        }

                        for (int p=0;p<stepsJsonArray.length();p++){
                            JSONObject stepsJsonObject = stepsJsonArray.getJSONObject(p);
                            int stepId= stepsJsonObject.getInt("id");
                            String shortDescription = stepsJsonObject.getString("shortDescription");
                            String description = stepsJsonObject.getString("description");
                            String videoUrl = stepsJsonObject.getString("videoURL");
                            String thumbnailUrl = stepsJsonObject.getString("thumbnailURL");
                            steps.add(new StepsObject(stepId,shortDescription,description,videoUrl,thumbnailUrl));
                        }
                        Log.v("ingredients count "+l,ingredients.size()+" ");
                        Log.v("steps count "+l,steps.size()+" ");
                        returnedRecipeList.add(new RecipeObject(recipeId,name,ingredients,steps,servings));
                    }
                }catch (JSONException e){
                    e.printStackTrace();

                }
            }
            return returnedRecipeList;
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            recipeObjectArrayList.clear();
            ingredientObjectArrayList.clear();
        }

        @Override
        public int getCount() {
            return recipeObjectArrayList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.sv_widget_recipe);
            if (recipeObjectArrayList !=null){
                ingredientObjectArrayList = recipeObjectArrayList.get(position).ingredientsArrayList;
                StringBuilder ingredientDescription = new StringBuilder();
                for (IngredientObject ingredient: ingredientObjectArrayList){
                    ingredientDescription.append(" > ");
                    ingredientDescription.append(ingredient.quantity);
                    ingredientDescription.append(" ");
                    ingredientDescription.append(ingredient.measure);
                    ingredientDescription.append(" @ ");
                    ingredientDescription.append(ingredient.ingredients);
                    ingredientDescription.append("\n");
                }
                remoteViews.setTextViewText(R.id.widget_stackview_recipe_title,recipeObjectArrayList.get(position).name);
                remoteViews.setTextViewText(R.id.widget_stackView_recipe_ingredients,ingredientDescription);
                Bundle bundle = new Bundle();
                bundle.putInt(PrWidgetRecipes.EXTRA_INGREDIENT_TAG, position);
                Intent fillInIntent = new Intent();
                fillInIntent.putExtras(bundle);
            }else {
            }
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


        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }
}
