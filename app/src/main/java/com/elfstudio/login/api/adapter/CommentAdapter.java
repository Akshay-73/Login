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
import com.elfstudio.login.api.pojo.CommentData;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context context;
    private List<CommentData> commentDataList;

    public CommentAdapter(Context context, List<CommentData> commentDataList) {
        this.context = context;
        this.commentDataList = commentDataList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate( R.layout.layout_comment_item, null);
        return new CommentViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, final int position) {

        holder.postId.setText("Post Id: " + commentDataList.get(position).getPostId());
        holder.id.setText("Id: " + commentDataList.get(position).getId());
        holder.name.setText("Name: " + commentDataList.get(position).getName());
        holder.email.setText("Email: " + commentDataList.get(position).getPostId());
        holder.body.setText("Body: " + commentDataList.get(position).getText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,commentDataList.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return commentDataList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder{

        TextView postId, id, name, email, body;

        CommentViewHolder(View itemView){
            super(itemView);

            postId = itemView.findViewById(R.id.tv_postId);
            id = itemView.findViewById(R.id.tv_commentId);
            name = itemView.findViewById(R.id.tv_commentName);
            email = itemView.findViewById(R.id.tv_commentEmail);
            body = itemView.findViewById(R.id.tv_commentText);
        }
    }
}
