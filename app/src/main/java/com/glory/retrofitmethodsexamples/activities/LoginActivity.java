package com.glory.retrofitmethodsexamples.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.glory.retrofitmethodsexamples.R;
import com.glory.retrofitmethodsexamples.api.RetrofitClient;
import com.glory.retrofitmethodsexamples.model.LoginResponse;
import com.glory.retrofitmethodsexamples.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.email_login);
        editTextPassword = findViewById(R.id.password_login);

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

    public void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a valid email address");
            editTextPassword.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Password should be at least 6 character long");
            editTextPassword.requestFocus();
            return;
        }

//        Call<LoginResponse> call = RetrofitClient.getmInstance().getApi().userLogin(email, password);
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                LoginResponse loginResponse = response.body();
//                if(!loginResponse.isError()){
//                    SharedPrefManager.getInstance(LoginActivity.this)
//                            .saveUser(loginResponse.getUser());
//
//                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//
//
//                }else{
//                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                userLogin();
                break;
            case R.id.register:
                startActivity(new Intent(this, MainActivity.class));
        }

    }

    public void login(View view) {
        userLogin();
    }
}
