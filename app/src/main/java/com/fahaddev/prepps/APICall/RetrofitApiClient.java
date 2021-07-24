package com.fahaddev.prepps.APICall;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fahaddev.prepps.helpers.StaticClass.currentUser;

public  class RetrofitApiClient {
    public static Retrofit retrofit;
    public static APIMethodInterface createRetrofitApi(String baseUrl){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + currentUser.getToken())
                        .build();
                return chain.proceed(newRequest);
            }
        });

        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.readTimeout(60, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        httpClient.addInterceptor(logging);
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(APIMethodInterface.class);
    }
    private static String getFirstCn(X509Certificate cert) {
        String subjectPrincipal = cert.getSubjectX500Principal().toString();
        for (String token : subjectPrincipal.split(",")) {
            int x = token.indexOf("CN=");
            if (x >= 0) {
                return token.substring(x + 3);
            }
        }
        return null;
    }
    Retrofit getRetrofit(){
        return retrofit;
    }
}