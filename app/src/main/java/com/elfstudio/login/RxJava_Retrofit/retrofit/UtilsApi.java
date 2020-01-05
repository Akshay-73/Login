package com.elfstudio.login.RxJava_Retrofit.retrofit;

public class UtilsApi {

    //Urls
    private static final String BASE_URL_API = "https://api.github.com/";
    private static final String TEST_URL_API = "https://jsonplaceholder.typicode.com/";

    public static BaseApiService getApiServices(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

    public static BaseApiService getTestApiServices(){
        return RetrofitClient.getClient(TEST_URL_API).create(BaseApiService.class);
    }

}
