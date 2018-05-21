package app.example.baking.bakingapp.ui.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.example.baking.bakingapp.R;
import app.example.baking.bakingapp.adapter.IngredientsAdapter;
import app.example.baking.bakingapp.model.Ingredients;
import app.example.baking.bakingapp.model.Recipe;


public class IngredientsFragment extends Fragment implements IngredientsAdapter.IngredientsAdapterOnClickHandler {


    private static final String TAG = IngredientsFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private IngredientsAdapter mIngredientsAdapter;

    private ArrayList<Ingredients> ingredient;


    public IngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        mRecyclerView = rootView.findViewById(R.id.rv_ingredients_fragment);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);



        Recipe recipeObject = getActivity().getIntent().getParcelableExtra("recipeObject");
        if (recipeObject != null) {

         String recipeName =   recipeObject.getName();

            ingredient =  recipeObject.getIngredients();

            mIngredientsAdapter = new IngredientsAdapter(this);
            mIngredientsAdapter.setIngredientData(ingredient);

            mRecyclerView.setAdapter(mIngredientsAdapter);

            getActivity().setTitle(recipeName);

            Log.e(TAG, "ingredient " + ingredient );
        }



        return rootView;
    }



    //https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }




    @Override
    public void onClick(Ingredients ingredientsItem) {
        Log.e(TAG, "click ingredients " );
    }
}
