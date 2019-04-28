package com.glory.retrofitmethodsexamples.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.glory.retrofitmethodsexamples.R;
import com.glory.retrofitmethodsexamples.model.DefaultResponse;
import com.glory.retrofitmethodsexamples.api.RetrofitClient;
import com.glory.retrofitmethodsexamples.storage.SharedPrefManager;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextName, editTextPassword, editTextSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.email);
        editTextName = findViewById(R.id.name);
        editTextPassword = findViewById(R.id.password);
        editTextSchool = findViewById(R.id.school);

        findViewById(R.id.button_signup).setOnClickListener(this);
        findViewById(R.id.textview_login).setOnClickListener(this);

    }

    //Onstart will check if the user is already logged in
    // if the user is already logged in the start intent will be called
    @Override
    protected void onStart(){
        super.onStart();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void userSignup(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();String school = editTextSchool.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
        }else{
            editTextEmail.setError(null);
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a valid email address");
            editTextEmail.requestFocus();
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
        }
        if(password.length() < 6){
            editTextPassword.setError("Password should be at least 6 characters long");
            editTextPassword.requestFocus();
        }
        if(name.isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
        }
        if(school.isEmpty()){
            editTextSchool.setError("School is required");
            editTextSchool.requestFocus();
        }

        // Api call for create user
        Call<DefaultResponse> call = RetrofitClient.
                getmInstance()
                .getApi()
                .createUser(email,password,name,school);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code() == 201){
                    DefaultResponse defaultResponse = response.body();
                    Toast.makeText(MainActivity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }else if(response.code() == 422){
                    Toast.makeText(MainActivity.this, "User already exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_signup:
                userSignup();
                break;
            case R.id.textview_login:
                startActivity(new Intent(this,LoginActivity.class));
                break;
                default:
        }
    }

//    public void login(View view) {
//        startActivity(new Intent(this,LoginActivity.class));
//
//    }
}
