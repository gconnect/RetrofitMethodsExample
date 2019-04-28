package com.glory.retrofitmethodsexamples.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.glory.retrofitmethodsexamples.R;
import com.glory.retrofitmethodsexamples.activities.LoginActivity;
import com.glory.retrofitmethodsexamples.activities.MainActivity;
import com.glory.retrofitmethodsexamples.activities.ProfileActivity;
import com.glory.retrofitmethodsexamples.api.RetrofitClient;
import com.glory.retrofitmethodsexamples.model.DefaultResponse;
import com.glory.retrofitmethodsexamples.model.LoginResponse;
import com.glory.retrofitmethodsexamples.model.User;
import com.glory.retrofitmethodsexamples.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private EditText email, name, school, current_password, new_password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = view.findViewById(R.id.editTextEmail);
        name = view.findViewById(R.id.editTextName);
        school = view.findViewById(R.id.editTextSchool);
        current_password = view.findViewById(R.id.currentPassword);
        new_password = view.findViewById(R.id.newPassword);

        view.findViewById(R.id.button_save).setOnClickListener(this);
        view.findViewById(R.id.button_delete_profile).setOnClickListener(this);
        view.findViewById(R.id.button_chanage_password).setOnClickListener(this);
        view.findViewById(R.id.button_logout).setOnClickListener(this);

    }

    // This methods updates the user profile
    private void updateProfile(){
        String mEmail = email.getText().toString();
        String mName = name.getText().toString();
        String mSchool = school.getText().toString();


        if(mEmail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
            email.setError("Enter a valid email address");
            email.requestFocus();
            return;
        }

        if(mName.isEmpty()){
            name.setError("password is required");
            name.requestFocus();
            return;
        }

        if(mSchool.isEmpty()){
            school.setError("password is required");
            school.requestFocus();
            return;
        }

        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        Call<LoginResponse> call = RetrofitClient.getmInstance().getApi().updateUser(
                user.getId(),
                mEmail,
                mName,
                mSchool
        );

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if(!response.body().isError()){
                    SharedPrefManager.getInstance(getActivity()).getUser();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    public void updatePassword(){
        String mCurrentPassword = current_password.getText().toString().trim();
        String mNewPassword = new_password.getText().toString().trim();

        if(mCurrentPassword.isEmpty()){
            current_password.setError("password is required");
            current_password.requestFocus();
            return;
        }

        if(mNewPassword.isEmpty()){
            new_password.setError("Enter new password");
            new_password.requestFocus();
            return;
        }


        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        Call<DefaultResponse> call = RetrofitClient.getmInstance().getApi()
                .updatePassword(mCurrentPassword, mNewPassword, user.getEmail());
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });

    }

    private void logout(){
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void deleteUser(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure?");
        builder.setMessage("This action is irreversible");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                User user = SharedPrefManager.getInstance(getActivity()).getUser();
                Call<DefaultResponse> call = RetrofitClient.getmInstance().getApi().deleteUser(user.getId());
                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        if(!response.body().isError()){
                            SharedPrefManager.getInstance(getActivity()).clear();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {

                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_save:
                updateProfile();
                break;
            case R.id.button_chanage_password:
                updatePassword();
                break;
            case R.id.button_delete_profile:
                deleteUser();
                break;
            case R.id.button_logout:
                logout();
                break;
        }

    }
}
