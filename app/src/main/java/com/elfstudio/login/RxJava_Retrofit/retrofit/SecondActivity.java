package com.elfstudio.login.RxJava_Retrofit.retrofit;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.elfstudio.login.R;
import com.elfstudio.login.RxJava_Retrofit.adapter.ReposAdapter;
import com.elfstudio.login.RxJava_Retrofit.pojo.Repo;
import com.elfstudio.login.RxJava_Retrofit.pojo.ResponseRepos;
import com.elfstudio.login.base.BaseActivity;
import com.elfstudio.login.databinding.ActivitySecondBinding;
import com.elfstudio.login.ui.main.MainHandler;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SecondActivity extends BaseActivity implements MainHandler {

    ActivitySecondBinding binding;
    BaseApiService mApiService;
    ReposAdapter mRepoAdapter;
    List<Repo> repoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second);

        mApiService = UtilsApi.getApiServices();

        mRepoAdapter = new ReposAdapter(this, repoList, null);
        binding.rvRepos.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRepos.setItemAnimator(new DefaultItemAnimator());
        binding.rvRepos.setHasFixedSize(true);
        binding.rvRepos.setAdapter(mRepoAdapter);

        binding.etUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String username = binding.etUsername.getText().toString();
                    requestRepos(username);
                    return true;
                }
                return false;
            }
        });

    }

    private void requestRepos(String username) {
        binding.pbLoading.setVisibility(View.VISIBLE);

        mApiService.requestRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ResponseRepos>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ResponseRepos> responseRepos) {

                        for (int i = 0; i < responseRepos.size(); i++) {
                            String name = responseRepos.get(i).getName();
                            String description = responseRepos.get(i).getDescription();
                            String fullName = responseRepos.get(i).getFullName();
                            String updatedAt = responseRepos.get(i).getUpdatedAt();

                            repoList.add(new Repo(name, description, fullName, updatedAt));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                       showMsg(e.getMessage());
                        binding.pbLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        binding.pbLoading.setVisibility(View.GONE);
                        showMsg("Searched Data");

                        mRepoAdapter = new ReposAdapter(SecondActivity.this, repoList, null);
                        binding.rvRepos.setAdapter(mRepoAdapter);
                        mRepoAdapter.notifyDataSetChanged();
                    }
                });
    }
}
