package com.glory.retrofitmethodsexamples.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glory.retrofitmethodsexamples.R;
import com.glory.retrofitmethodsexamples.storage.SharedPrefManager;

public class HomeFragment extends Fragment {

    private TextView email, name, school;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = view.findViewById(R.id.textviewEmail);
        name = view.findViewById(R.id.textviewName);
        school = view.findViewById(R.id.textviewSchool);

        // fetching user details from ShareprefManager to display on the home screen
        email.setText(SharedPrefManager.getInstance(getActivity()).getUser().getEmail());
        name.setText(SharedPrefManager.getInstance(getActivity()).getUser().getName());
        school.setText(SharedPrefManager.getInstance(getActivity()).getUser().getSchool());

    }
}
