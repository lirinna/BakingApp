package app.example.baking.bakingapp.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.example.baking.bakingapp.R;
import app.example.baking.bakingapp.model.Step;


public class StepOverviewFragment extends Fragment {

    private static final String TAG = StepOverviewFragment.class.getSimpleName();

    public StepOverviewFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_step_overview, container, false);



        Step stepObject = getActivity().getIntent().getParcelableExtra("stepObject");
        if (stepObject != null) {

        String    description =  stepObject.getDescription();

            TextView mName = rootView.findViewById(R.id.tv_step_overview_description);
            mName.setText(description);



            Log.e(TAG, "description " + description );

        }

        return rootView;
    }



}
