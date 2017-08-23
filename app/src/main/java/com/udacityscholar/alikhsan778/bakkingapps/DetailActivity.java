package com.udacityscholar.alikhsan778.bakkingapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* cus alikhsan778
* */

public class DetailActivity extends AppCompatActivity implements FrIngredientsWithSteps.IngredientWithStepsOnPushListener {

    RecipeObject recipeObject;
    FrIngredientsWithSteps frIngredientsWithSteps;
    FrVideos frVideos;
    @BindView(R.id.img_recipe)
    ImageView imageRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        recipeObject = getIntent().getParcelableExtra("RecipeObject");
        ArrayList<IngredientObject> ingredientArrayList = recipeObject.ingredientsArrayList;
        ArrayList<StepsObject> stepsArrayList = recipeObject.stepsArrayList;

        String recipeName = String.valueOf(recipeObject.name);
        int recipeId = recipeObject.recipeId;
        if (recipeId == 1) {
            imageRecipe.setImageResource(R.drawable.img_nutella_pie);
        } else if (recipeId == 2) {
            imageRecipe.setImageResource(R.drawable.img_brownie);
        } else if (recipeId == 3) {
            imageRecipe.setImageResource(R.drawable.img_yellow_cake);
        } else if (recipeId == 4) {
            imageRecipe.setImageResource(R.drawable.img_chesee_cake);
        } else {
            imageRecipe.setImageResource(R.mipmap.ic_launcher);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(recipeName);
        collapsingToolbar.setExpandedTitleColor(0);

        Bundle datas = new Bundle();
        datas.putParcelableArrayList("Ingredients", ingredientArrayList);
        datas.putParcelableArrayList("StepsObject", stepsArrayList);
        frIngredientsWithSteps = new FrIngredientsWithSteps();
        frIngredientsWithSteps.setArguments(datas);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_fragment_ingredients_steps, frIngredientsWithSteps).commit();


        if (findViewById(R.id.frame_fragment_video_description) != null) {
            frVideos = new FrVideos();
            Bundle datasVideo = new Bundle();
            datasVideo.putString("Step URL", stepsArrayList.get(0).videoUrl);
            datasVideo.putString("Step Description", stepsArrayList.get(0).description);
            datasVideo.putString("Short Description", stepsArrayList.get(0).shortDescription);
            frVideos.setArguments(datasVideo);

            FragmentManager videosFragmentManager = getSupportFragmentManager();
            videosFragmentManager.beginTransaction().add(R.id.frame_fragment_video_description, frVideos).commit();
        }
    }

    @Override
    public void onIngredientStepItemPushed(Bundle bundle) {

        if (findViewById(R.id.frame_fragment_video_description) == null) {
            Intent intent = new Intent(this, VideosActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {

            frVideos = new FrVideos();
            frVideos.setArguments(bundle);
            FragmentManager videosFragmentManager = getSupportFragmentManager();
            videosFragmentManager.beginTransaction().add(R.id.frame_fragment_video_description, frVideos).commit();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
