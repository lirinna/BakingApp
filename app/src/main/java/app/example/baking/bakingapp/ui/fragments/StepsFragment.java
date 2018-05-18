package app.example.baking.bakingapp.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import app.example.baking.bakingapp.ui.activities.StepOverviewActivity;


public class StepsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler {
    private static final String TAG = StepsFragment.class.getSimpleName();
    private static boolean isTwoPane;
    private RecyclerView mRecyclerView;
    private StepsAdapter mStepsAdapter;
    private ArrayList<Step> stepsList;

    public StepsFragment() {
        // Required empty public constructor
    }

    public static void setTwoPane(Boolean flag) {
        isTwoPane = flag;
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

            stepsList = recipeObject.getSteps();

            mStepsAdapter = new StepsAdapter(this);
            mStepsAdapter.setStepsData(stepsList);
            mRecyclerView.setAdapter(mStepsAdapter);

            Log.e(TAG, "step " + stepsList);
        }
        return rootView;
    }

    @Override
    public void onClick(Step stepItem) {
        if (!isTwoPane) {
            Log.e(TAG, "Single Screen ");

            Intent intent = new Intent(getActivity(), StepOverviewActivity.class);
            intent.putExtra("stepObject", stepItem);
            startActivity(intent);
        } else {
            Log.e(TAG, "Double Screen ");

            StepOverviewFragment stepsOverviewFragment = new StepOverviewFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container_step_overview, stepsOverviewFragment);
            transaction.addToBackStack(null);

            Bundle stepBundle = new Bundle();
            stepBundle.putParcelable("bundleStep", stepItem);
            StepOverviewFragment.putItemT(stepBundle);

            transaction.commit();
        }
    }
}
