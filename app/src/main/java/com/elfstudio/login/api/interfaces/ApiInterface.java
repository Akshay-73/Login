package com.elfstudio.login.api.interfaces;

import com.elfstudio.login.api.pojo.CommentData;
import com.elfstudio.login.api.pojo.PostData;
import com.elfstudio.login.api.pojo.SignUpResponse;
import com.elfstudio.login.api.pojo.UserListResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiInterface {

    @FormUrlEncoded // annotation used in post type requests
    @POST("/retrofit/register.php")
            // Api end point
    Call<SignUpResponse> registration(@Field("name") String name,
                                      @Field("email") String email,
                                      @Field("password") String password,
                                      @Field("logintype") String logintype);

    /* In registration method @Field used to set the keys and String data type is representing
     its a string type value and callback is used to get the response from api and it will set it in our POJO class */

    @GET("/retrofit/getuser.php")

    Call<List<UserListResponse>> getUserList();


    /* UserListResponse is POJO class to get the data from API,
     we use List<UserListResponse> in callback because the data in our API is starting from JSONArray*/
    @GET("posts")
    Call<List<PostData>> getPostList(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("posts")
    Call<List<PostData>> getPostList(@QueryMap Map<String, String> parameters);

    @GET("posts/{id}/comments")
    Call<List<CommentData>> getCommentList(@Path("id") int postId);

    @GET
    Call<List<CommentData>> getCommentList(@Url String url);

    @POST("posts")
    Call<PostData> createPost(@Body PostData postData);

    @FormUrlEncoded
    @POST("posts")
    Call<PostData> createPost(
            @Field("userID") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    @PUT("posts/{id}")
    Call<PostData> putPost(@Path("id") int id, @Body PostData postData);

    @PATCH("posts/{id}")
    Call<PostData> patchPost(@Path("id") int id, @Body PostData postData);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

}
