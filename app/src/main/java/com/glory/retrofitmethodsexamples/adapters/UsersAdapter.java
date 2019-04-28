package com.glory.retrofitmethodsexamples.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glory.retrofitmethodsexamples.R;
import com.glory.retrofitmethodsexamples.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder>{

    private Context mContext;
    private List<User> mUsers;

    public UsersAdapter(Context mContext, List<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(this.mContext).inflate(R.layout.recycler_users, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {

        User currentUser = mUsers.get(position);

        String email = currentUser.getEmail();
        String name = currentUser.getName();
        String school = currentUser.getSchool();

        holder.mEmail.setText(email);
        holder.mName.setText(name);
        holder.mSchool.setText(school);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder{

        private TextView mEmail;
        private TextView mName;
        private TextView mSchool;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            mEmail = itemView.findViewById(R.id.textviewEmail);
            mName = itemView.findViewById(R.id.textviewName);
            mSchool = itemView.findViewById(R.id.textviewSchool);
        }
    }
}
