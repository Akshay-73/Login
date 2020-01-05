package com.elfstudio.login.RxJava_Retrofit.retrofit;


import com.elfstudio.login.RxJava_Retrofit.pojo.ResponseRepos;
import com.elfstudio.login.api.pojo.PostData;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseApiService {

    @GET("users/{username}/repos")
    Observable<List<ResponseRepos>> requestRepos(@Path("username") String username);

    @GET("posts")
    Observable<List<PostData>> getPostList();

}
