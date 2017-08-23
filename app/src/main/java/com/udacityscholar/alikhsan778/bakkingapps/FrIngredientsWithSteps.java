package com.udacityscholar.alikhsan778.bakkingapps;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * cus alikhsan778
 */

public class FrIngredientsWithSteps extends Fragment implements RvStepsAdapter.StepsOnClickListener{

    private ArrayList<IngredientObject> ingredientObjectArrayList;
    private ArrayList<StepsObject> stepsObjectArrayList;


    @BindView(R.id.rv_ingredients_list)
    RecyclerView ingredientsRecyclerView;
    @BindView(R.id.rv_steps_list)
    RecyclerView stepsRecyclerView;

    public FrIngredientsWithSteps(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_ingredients_steps,container,false);
        ingredientObjectArrayList = getArguments().getParcelableArrayList("Ingredients");
        stepsObjectArrayList = getArguments().getParcelableArrayList("StepsObject");

        ButterKnife.bind(this,rootView);

        RvIngredientsAdapter ingredientsRecyclerViewAdapter=new RvIngredientsAdapter(ingredientObjectArrayList);
        ingredientsRecyclerView.setAdapter(ingredientsRecyclerViewAdapter);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RvStepsAdapter stepsRecyclerViewAdapter = new RvStepsAdapter(stepsObjectArrayList,this);
        stepsRecyclerView.setAdapter(stepsRecyclerViewAdapter);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return rootView;
    }

    @Override
    public void onStepItemClicked(int position) {

        IngredientWithStepsOnPushListener onPushListener=(IngredientWithStepsOnPushListener) getActivity();
        Bundle bundle = new Bundle();
        bundle.putString("Step URL",stepsObjectArrayList.get(position).videoUrl);
        bundle.putString("Step Description",stepsObjectArrayList.get(position).description);
        bundle.putString("Step thumbnail",stepsObjectArrayList.get(position).thumbnailUrl);
        onPushListener.onIngredientStepItemPushed(bundle);
        Log.v("STEPS FRAGMENTS","Position clicked");
    }

    public interface IngredientWithStepsOnPushListener{
        void onIngredientStepItemPushed(Bundle bundle);
    }
}
