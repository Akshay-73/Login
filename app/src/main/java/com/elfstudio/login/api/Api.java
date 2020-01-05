package com.elfstudio.login.api;

import com.elfstudio.login.api.interfaces.ApiInterface;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static Retrofit retrofit = null;


    public static ApiInterface getClient(){

        //change the base url for different purpose
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://mobileappdatabase.in/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ApiInterface.class);
    }




    public static ApiInterface getPost(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ApiInterface.class);
    }

    public static ApiInterface getComment(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ApiInterface.class);
    }
}
