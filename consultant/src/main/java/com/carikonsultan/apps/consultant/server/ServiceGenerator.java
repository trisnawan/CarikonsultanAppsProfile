package com.carikonsultan.apps.consultant.server;

import android.content.Context;
import android.util.Base64;

import com.carikonsultan.apps.consultant.core.Credential;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static OkHttpClient.Builder builder(){
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient().newBuilder();
        okhttpBuilder.connectTimeout(60, TimeUnit.SECONDS);
        okhttpBuilder.writeTimeout(60, TimeUnit.SECONDS);
        okhttpBuilder.readTimeout(60, TimeUnit.SECONDS);
        return okhttpBuilder;
    }
    private static HttpLoggingInterceptor interceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
    public static <S> S createBaseService(Context context, Class<S> serviceClass) {
        OkHttpClient.Builder okhttpBuilder = builder();

        Credential credential = new Credential(context);
        String key = credential.getKey();
        String username = credential.getUsername();
        String password = credential.getPassword();
        String base = username + ":" + password;
        String authorization = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        okhttpBuilder.addInterceptor(interceptor());

        okhttpBuilder.addInterceptor(chain -> {
            Request request = chain.request();
            Request newReq = request.newBuilder()
                    .header("Accept", "application/json")
                    .header("key", key)
                    .header("Authorization", authorization)
                    .build();
            return chain.proceed(newReq);
        });

        OkHttpClient client = okhttpBuilder.build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .serializeNulls()
                .create();

        String BASE_URL = "https://api.carikonsultan.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(serviceClass);
    }
}
