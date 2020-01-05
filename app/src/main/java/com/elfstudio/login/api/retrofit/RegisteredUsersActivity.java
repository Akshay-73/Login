package com.elfstudio.login.api.retrofit;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.elfstudio.login.R;
import com.elfstudio.login.api.Api;
import com.elfstudio.login.api.adapter.UsersAdapter;
import com.elfstudio.login.api.pojo.UserListResponse;
import com.elfstudio.login.base.BaseActivity;
import com.elfstudio.login.databinding.ActivityRegisteredUsersBinding;
import com.elfstudio.login.ui.main.MainHandler;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisteredUsersActivity extends BaseActivity implements MainHandler {

    ActivityRegisteredUsersBinding binding;
    List<UserListResponse> userListResponseData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registered_users);
        getUserListData();

    }

    private void getUserListData(){
        final ProgressDialog progressDialog = new ProgressDialog(RegisteredUsersActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        (Api.getClient().getUserList()).enqueue( new Callback<List<UserListResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<UserListResponse>> call, @NotNull Response<List<UserListResponse>> response) {
                assert response.body() != null;
                Log.d("responseGET", response.body().get(0).getName());
                progressDialog.dismiss(); //dismiss progress dialog
                userListResponseData = response.body();
                setDataInRecyclerView();
            }

            @Override
            public void onFailure(@NotNull Call<List<UserListResponse>> call, @NonNull Throwable t) {
                Toast.makeText(RegisteredUsersActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss(); //dismiss progress dialog
            }
        });

    }

    private void setDataInRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RegisteredUsersActivity.this);
        binding.rvRegisteredList.setLayoutManager(linearLayoutManager);
        // call the constructor of UsersAdapter to send the reference and data to Adapter
        UsersAdapter usersAdapter = new UsersAdapter(RegisteredUsersActivity.this, userListResponseData);
        binding.rvRegisteredList.setAdapter(usersAdapter); // set the Adapter to RecyclerView
    }
}
