package com.elfstudio.login.api.retrofit;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.elfstudio.login.R;
import com.elfstudio.login.RxJava_Retrofit.retrofit.BaseApiService;
import com.elfstudio.login.RxJava_Retrofit.retrofit.UtilsApi;
import com.elfstudio.login.api.Api;
import com.elfstudio.login.api.adapter.PostAdapter;
import com.elfstudio.login.api.pojo.PostData;
import com.elfstudio.login.base.BaseActivity;
import com.elfstudio.login.databinding.ActivityPostResultBinding;
import com.elfstudio.login.ui.main.MainHandler;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostResultActivity extends BaseActivity implements MainHandler {

    ActivityPostResultBinding binding;
    private List<PostData> postDataList;
    private PostData postResponse;
    BaseApiService baseApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_result);
        postDataList = new ArrayList<>();
        baseApiService = UtilsApi.getTestApiServices();
        //getPostData();
        //createPost();
        getPost();

        //updatePost();

        //deletePost();

//        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Validate(binding.userId) && Validate(binding.title) && Validate(binding.body)){
//                    createPost();
//                    binding.userId.getText().clear();
//                    binding.title.getText().clear();
//                    binding.body.getText().clear();
//                }
//
//            }
//        });



    }

    public void getPostData(){
        final ProgressDialog progressDialog = new ProgressDialog(PostResultActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        Map<String, String > parameters = new HashMap<>();
        parameters.put("userId","1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        (Api.getPost().getPostList(parameters/*new Integer[]{2,3,6},null,null*/)).enqueue( new Callback<List<PostData>>() {
            @Override
            public void onResponse(@NotNull Call<List<PostData>> call, @NotNull Response<List<PostData>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(PostResultActivity.this,response.code(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }

                progressDialog.dismiss();
                postDataList = response.body();
                setPostRecyclerView();

            }

            @Override
            public void onFailure(@NotNull Call<List<PostData>> call, @NotNull Throwable t) {
                Toast.makeText(PostResultActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void createPost(){
        PostData post = new PostData(23,"New Title", "New Text");

        final ProgressDialog progressDialog = new ProgressDialog(PostResultActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message here
        progressDialog.show(); // this will show the progress dialog

        Api.getPost().createPost(post).enqueue(new Callback<PostData>() {    /*Integer.parseInt(binding.userId.getText().toString()),
                binding.title.getText().toString(),
                binding.body.getText().toString()*/
            @Override
            public void onResponse(@NotNull Call<PostData> call, @NotNull Response<PostData> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(PostResultActivity.this,response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }

                postResponse = response.body();


                String content = "";
                assert postResponse != null;
                content += "code: " + response.code() + "\n";
                content+= "Id: " + postResponse.getId() + "\n";
                content+= "User Id: " + postResponse.getUserId()+ "\n";
                content+= "Title: " + postResponse.getTitle() + "\n";
                content+= "Text: " + postResponse.getText() + "\n";

                binding.tvPostResult.append(content);
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(@NotNull Call<PostData> call, @NotNull Throwable t) {
                Toast.makeText(PostResultActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void getPost(){

        final ProgressDialog progressDialog = new ProgressDialog(PostResultActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message here
        progressDialog.show(); // this will show the progress dialog

        baseApiService.getPostList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PostData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PostData> postData) {

                        for (int i=0; i< postData.size(); i++){
                            int userId = postData.get(i).getUserId();
                            int id = postData.get(i).getId();
                            String title = postData.get(i).getTitle();
                            String body = postData.get(i).getText();

                            postDataList.add(new PostData(userId,id,title,body));

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                        showMsg("Searched Posts");
                        setPostRecyclerView();
                    }
                });
    }

    private void updatePost(){
        PostData postData = new PostData(12,null, "New Text");

        Api.getPost().patchPost(5,postData).enqueue(new Callback<PostData>() {  // patch replaces only the given value but put replaces all the values
            @Override
            public void onResponse(@NotNull Call<PostData> call, @NotNull Response<PostData> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(PostResultActivity.this,response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }

                postResponse = response.body();


                String content = "";
                assert postResponse != null;
                content += "code: " + response.code() + "\n";
                content+= "Id: " + postResponse.getId() + "\n";
                content+= "User Id: " + postResponse.getUserId()+ "\n";
                content+= "Title: " + postResponse.getTitle() + "\n";
                content+= "Text: " + postResponse.getText() + "\n";

                binding.tvPostResult.append(content);

            }

            @Override
            public void onFailure(@NotNull Call<PostData> call, @NotNull Throwable t) {
                Toast.makeText(PostResultActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void deletePost(){
        Api.getPost().deletePost(5).enqueue(new Callback<Void>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                binding.tvPostResult.setText("code: " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(PostResultActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean Validate(EditText editText){

        if (editText.getText().toString().trim().length() > 0){
            return true;
        }

        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    public void setPostRecyclerView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(PostResultActivity.this);
        binding.rvPost.setLayoutManager(layoutManager);

        PostAdapter postAdapter = new PostAdapter(this, postDataList);
        binding.rvPost.setAdapter(postAdapter);
        postAdapter.notifyDataSetChanged();

    }
}
