package com.glory.retrofitmethodsexamples.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glory.retrofitmethodsexamples.R;
import com.glory.retrofitmethodsexamples.adapters.UsersAdapter;
import com.glory.retrofitmethodsexamples.api.RetrofitClient;
import com.glory.retrofitmethodsexamples.model.User;
import com.glory.retrofitmethodsexamples.model.UsersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<User> mUsers;
    UsersAdapter usersAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.users_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Call<UsersResponse> call = RetrofitClient.getmInstance().getApi().getUsers();
        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                mUsers = response.body().getUsers();
                usersAdapter =new UsersAdapter(getContext(), mUsers);
                recyclerView.setAdapter(usersAdapter);
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {

            }
        });


    }
}
