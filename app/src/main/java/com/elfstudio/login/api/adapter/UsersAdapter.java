package com.elfstudio.login.api.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elfstudio.login.R;
import com.elfstudio.login.api.pojo.UserListResponse;


import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private Context context;
    private List<UserListResponse> userListResponseData;

    public UsersAdapter(Context context, List<UserListResponse> userListResponseData) {
        this.context = context;
        this.userListResponseData = userListResponseData;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate( R.layout.layout_registered_list,null);
        return new UsersViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, final int position) {
        //set data

        holder.name.setText("Name: " + userListResponseData.get(position).getName());
        holder.email.setText("Email: " + userListResponseData.get(position).getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, userListResponseData.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userListResponseData.size();
    }

    static class UsersViewHolder extends RecyclerView.ViewHolder{

        TextView name, email;

        UsersViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.userEmail);
        }
    }
}
