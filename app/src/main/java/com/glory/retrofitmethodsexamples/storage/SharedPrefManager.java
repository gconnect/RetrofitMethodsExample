package com.glory.retrofitmethodsexamples.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.glory.retrofitmethodsexamples.model.User;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME ="my_shared_pref";
    private static SharedPrefManager mInstance;
    private Context mContext;

    private SharedPrefManager(Context context) {
        this.mContext = context;
    }

    // method to create a single instance of SharedPref {Sigleton pattern}
    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    // Share preference to save the user
    public void saveUser(User user){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("school", user.getSchool());
        editor.apply();

    }

    // To check if user is logged in
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, mContext.MODE_PRIVATE);
         return sharedPreferences.getInt("id", -1) != -1;
        }


        public User getUser(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, mContext.MODE_PRIVATE);
            User user = new User(
                    sharedPreferences.getInt("id", -1),
                    sharedPreferences.getString("email", null),
                    sharedPreferences.getString("name", null),
                    sharedPreferences.getString("school", null)
            );

            return user;
        }

        public void clear(){
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, mContext.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

        }
}
