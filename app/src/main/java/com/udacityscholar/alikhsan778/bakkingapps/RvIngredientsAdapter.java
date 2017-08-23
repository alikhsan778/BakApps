package com.udacityscholar.alikhsan778.bakkingapps;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * alikhsan778
 * cus
 */

public class RvIngredientsAdapter extends RecyclerView.Adapter<RvIngredientsAdapter.IngredientViewHolder> {

    private ArrayList<IngredientObject> ingredientObjectArrayList;

    public RvIngredientsAdapter(ArrayList<IngredientObject> ingredientObjectArrayList){
        this.ingredientObjectArrayList=ingredientObjectArrayList;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_ingredients,parent,false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        String quantity = ingredientObjectArrayList.get(position).quantity + " "+ ingredientObjectArrayList.get(position).measure;
        holder.name_ingredients.setText(ingredientObjectArrayList.get(position).ingredients);
        holder.quant_ingredients.setText(quantity);
    }

    @Override
    public int getItemCount() {
        return ingredientObjectArrayList.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_ingredient_name)
        TextView name_ingredients;
        @BindView(R.id.tv_ingredient_quantity)
        TextView quant_ingredients;

        public IngredientViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }

    }
}
