package com.elfstudio.login.api.retrofit;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.elfstudio.login.R;
import com.elfstudio.login.api.Api;
import com.elfstudio.login.api.adapter.CommentAdapter;
import com.elfstudio.login.api.pojo.CommentData;
import com.elfstudio.login.base.BaseActivity;
import com.elfstudio.login.databinding.ActivityCommentResultBinding;
import com.elfstudio.login.ui.main.MainHandler;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentResultActivity extends BaseActivity implements MainHandler {

    ActivityCommentResultBinding binding;
    List<CommentData> commentDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment_result);

        getCommentData();
    }

    private void getCommentData(){
        final ProgressDialog progressDialog = new ProgressDialog(CommentResultActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        (Api.getComment().getCommentList("posts/1/comments")).enqueue( new Callback<List<CommentData>>() {
            @Override
            public void onResponse(@NotNull Call<List<CommentData>> call, @NotNull Response<List<CommentData>> response) {
                if (!response.isSuccessful()){
                    showMsg(String.valueOf(response.code()));
                    progressDialog.dismiss();
                    return;
                }

                progressDialog.dismiss();
                commentDataList = response.body();
                setCommentRecycler();

            }

            @Override
            public void onFailure(@NotNull Call<List<CommentData>> call, @NotNull Throwable t) {
                showMsg(t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void setCommentRecycler(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvComment.setLayoutManager(layoutManager);

        CommentAdapter commentAdapter = new CommentAdapter(this, commentDataList);
        binding.rvComment.setAdapter(commentAdapter);
    }
}
