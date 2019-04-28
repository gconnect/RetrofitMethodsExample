package com.glory.retrofitmethodsexamples.api;

import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //Basic Auth
    private static final String AUTH = "Basic " + Base64.encodeToString(("belalkhan:123456").getBytes(), Base64.NO_WRAP);

    public static final String BASE_URL = "http://iris.kodehauz.xyz/api/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(){

        // Basic authorization implementation starts here (optional)
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .addInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder  requestBuilder = original.newBuilder()
                                .addHeader("Authorization", AUTH)
                                .method(original.method(), original.body());
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                }
        ).build(); // Authorization code ends here

        retrofit = new Retrofit.Builder()  // retrofit client singleton implementaion states here
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient) // This add the okhttp client for authorization header
                .build();
    }

    public static synchronized RetrofitClient getmInstance(){
        if(mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
