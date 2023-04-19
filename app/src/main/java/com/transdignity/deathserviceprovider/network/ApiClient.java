package com.transdignity.deathserviceprovider.network;

import android.content.Context;

import com.transdignity.deathserviceprovider.utilities.MyConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ram Bhawan on 12/09/2018.
 */

public class ApiClient {
    public static final String BASE_URL = MyConstants.BASE_URL;
    private static Retrofit retrofit = null;

    private static Retrofit getClient(final Context context) {

        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request request = originalRequest.newBuilder()
                            //.addHeader("Authorization", MaraamSharedPrefrence.getInstance(context).getToken(""))
                            .addHeader("X-API-KEY", MyConstants.XAPIKEY)
                            .method(originalRequest.method(), originalRequest.body())
                            .build();

                    return chain.proceed(request);
                }
            });
            httpClient.connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .build();
            OkHttpClient client = httpClient.build();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static ApiInterface getConnection(Context context) {
        return getClient(context).create(ApiInterface.class);
    }

}
