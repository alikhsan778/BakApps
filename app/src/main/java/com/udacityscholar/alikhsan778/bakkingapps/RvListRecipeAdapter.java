package com.udacityscholar.alikhsan778.bakkingapps;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *alikhsa 778
 * cus
 */

public class RvListRecipeAdapter extends RecyclerView.Adapter<RvListRecipeAdapter.RecipeListViewHolder> {

    private ArrayList<RecipeObject> recipeArrayList;
    private RecipeListOnClickListenerInteface recipeListOnClickListenerInteface;

    public RvListRecipeAdapter(ArrayList<RecipeObject> recipeArrayList, RecipeListOnClickListenerInteface recipeListOnClickListenerInteface){
        this.recipeArrayList=recipeArrayList;
        this.recipeListOnClickListenerInteface=recipeListOnClickListenerInteface;
    }

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_recipe,parent,false);
        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeListViewHolder holder, int position) {
        int recipeId = recipeArrayList.get(position).recipeId;
        if (recipeId==1){
            holder.image_recipe.setImageResource(R.drawable.img_nutella_pie);
        }
        else if (recipeId==2){
            holder.image_recipe.setImageResource(R.drawable.img_brownie);
        }
        else if (recipeId==3){
            holder.image_recipe.setImageResource(R.drawable.img_yellow_cake);
        }
        else if (recipeId==4){
            holder.image_recipe.setImageResource(R.drawable.img_chesee_cake);
        }
        else   {
            holder.image_recipe.setImageResource(R.mipmap.ic_launcher);
        }
        String name = recipeArrayList.get(position).name;
        holder.recipe_name.setText(name);
        String servingsString = ""+ recipeArrayList.get(position).servings;
        String ingredientsValie = ""+recipeArrayList.get(position).ingredientsArrayList.size();
        String stepValie = ""+recipeArrayList.get(position).stepsArrayList.size();
        holder.recipeServings.setText(servingsString);
        holder.recipeIngredients.setText(ingredientsValie);
        holder.recipeSteps.setText(stepValie);
        holder.actionStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                RecipeObject recipe = recipeArrayList.get(position);
                recipeListOnClickListenerInteface.onRecipeItemClicked(recipe);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                RecipeObject recipe = recipeArrayList.get(position);
                recipeListOnClickListenerInteface.onRecipeItemClicked(recipe);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }

    public interface RecipeListOnClickListenerInteface{
        void onRecipeItemClicked(RecipeObject recipe);
    }


    public class RecipeListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image_recipe)
        ImageView image_recipe;
        @BindView(R.id.tv_recipe_name)
        TextView recipe_name;
        @BindView(R.id.tv_number_ingredients)
        TextView recipeIngredients;
        @BindView(R.id.tv_number_servings)
        TextView recipeServings;
        @BindView(R.id.tv_number_step)
        TextView recipeSteps;
        @BindView(R.id.start_cooking)
        ImageButton actionStarted;

        public RecipeListViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

}
