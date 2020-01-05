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
import com.elfstudio.login.api.pojo.PostData;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<PostData> postDataList;

    public PostAdapter(Context context, List<PostData> postDataList) {
        this.context = context;
        this.postDataList = postDataList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate( R.layout.layout_post_item, null);
        return new PostViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, final int position) {

        holder.id.setText("Id: " + postDataList.get(position).getId());
        holder.userId.setText("User Id: " + postDataList.get(position).getUserId());
        holder.title.setText("Title: " + postDataList.get(position).getTitle());
        holder.bodyText.setText("Text: " + postDataList.get(position).getText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, postDataList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return postDataList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder{

        TextView id, userId, title, bodyText;

        PostViewHolder(View itemView){
            super(itemView);

            id = itemView.findViewById(R.id.tv_id);
            userId = itemView.findViewById(R.id.tv_userId);
            title = itemView.findViewById(R.id.tv_title);
            bodyText = itemView.findViewById(R.id.tv_text);
        }
    }
}
