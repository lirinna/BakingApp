package app.example.baking.bakingapp.ui.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import app.example.baking.bakingapp.adapter.StepsAdapter;
import app.example.baking.bakingapp.model.Recipe;
import app.example.baking.bakingapp.model.Step;


public class StepsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler {


private static final String TAG = StepsFragment.class.getSimpleName();
private RecyclerView mRecyclerView;
private StepsAdapter mStepsAdapter;

private ArrayList<Step> stepsList;


    public StepsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        mRecyclerView = rootView.findViewById(R.id.rv_steps_fragment);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);



        Recipe recipeObject = getActivity().getIntent().getParcelableExtra("recipeObject");
        if (recipeObject != null) {

            stepsList =  recipeObject.getSteps();

            mStepsAdapter = new StepsAdapter(this,stepsList);

            mRecyclerView.setAdapter(mStepsAdapter);

            Log.e(TAG, "step " + stepsList );

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
    public void onClick(Step stepItem) {

    }
}
