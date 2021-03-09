package org.fhi360.lamis.mobile.lite.Service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Idris
 */

public class LocalApiService {
    public static Retrofit retrofit = null;
    String iPAddress;

    private Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(iPAddress)
                    .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        return client;
    }

    public <S> S createService(Class<S> serviceClass) {
        retrofit = builder.client(getClient()).build();
        return retrofit.create(serviceClass);
    }


}


