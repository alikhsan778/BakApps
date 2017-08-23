package com.udacityscholar.alikhsan778.bakkingapps;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RvListRecipeAdapter.RecipeListOnClickListenerInteface {


    @BindView(R.id.rv_recipe_list)
    RecyclerView recipeListRecyclerView;
    ArrayList<RecipeObject> recipeArrayList;
    RvListRecipeAdapter recipeListAdapter;
    CountingIdlingResource countingIdlingResource = new CountingIdlingResource("Check recipe list");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        recipeArrayList = new ArrayList<>();
        recipeListAdapter = new RvListRecipeAdapter(recipeArrayList,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,getResources().getInteger(R.integer.grid_column_type));
        recipeListRecyclerView.setAdapter(recipeListAdapter);
        recipeListRecyclerView.setLayoutManager(gridLayoutManager);
        recipeListRecyclerView.setHasFixedSize(true);



        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = getResources().getString(R.string.url);
        JsonArrayRequest jsonArrayRequest = getJsonArray(url);
        if (isNetworkAvailable()){
            countingIdlingResource.increment();
            requestQueue.add(jsonArrayRequest);
        }else{
            Toast toast = Toast.makeText(this,"Check your Connection!",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private JsonArrayRequest getJsonArray(String url) {
        return new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                recipeArrayList = parseRecipeJson(response);
                recipeListAdapter = new RvListRecipeAdapter(recipeArrayList,MainActivity.this);
                recipeListRecyclerView.setAdapter(recipeListAdapter);
                recipeListRecyclerView.invalidate();
                countingIdlingResource.decrement();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(MainActivity.this,"Check your Connection!",Toast.LENGTH_LONG);
                toast.show();
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

                for (int c=0;c<response.length();c++){
                    JSONObject obj = response.getJSONObject(c);
                    recipeId = obj.getInt("id");
                    name = obj.getString("name");
                    ingredients = new ArrayList<>();
                    steps = new ArrayList<>();
                    JSONArray ingredientsJsonArray = obj.getJSONArray("ingredients");
                    JSONArray stepsJsonArray = obj.getJSONArray("steps");
                    servings = obj.getInt("servings");


                    for (int u=0;u<ingredientsJsonArray.length();u++){
                        JSONObject ingredientsObject = ingredientsJsonArray.getJSONObject(u);
                        double quantity = ingredientsObject.getDouble("quantity");
                        String measure = ingredientsObject.getString("measure");
                        String ingredient = ingredientsObject.getString("ingredient");
                        ingredients.add(new IngredientObject(quantity,measure,ingredient));
                    }

                    for (int s=0;s<stepsJsonArray.length();s++){
                        JSONObject stepsJsonObject = stepsJsonArray.getJSONObject(s);
                        int stepId= stepsJsonObject.getInt("id");
                        String shortDescription = stepsJsonObject.getString("shortDescription");
                        String description = stepsJsonObject.getString("description");
                        String videoUrl = stepsJsonObject.getString("videoURL");
                        String thumbnailUrl = stepsJsonObject.getString("thumbnailURL");
                        steps.add(new StepsObject(stepId,shortDescription,description,videoUrl,thumbnailUrl));
                    }
                    Log.v("ingredients count "+c,ingredients.size()+" ");
                    Log.v("steps count "+c,steps.size()+" ");
                    returnedRecipeList.add(new RecipeObject(recipeId,name,ingredients,steps,servings));
                }
            }catch (JSONException e){
                e.printStackTrace();

            }
        }else{
            Toast toast = Toast.makeText(MainActivity.this,"Check your Connection!",Toast.LENGTH_LONG);
            toast.show();
        }
        return returnedRecipeList;
    }

    @Override
    public void onRecipeItemClicked(RecipeObject recipe) {
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra("RecipeObject",recipe);
        startActivity(intent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
