package com.douglas.whatafridge.Model;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.douglas.whatafridge.Controller.SpoonacularController;

public class RetrofitClient {

    private static final String BASE_URL = SpoonacularController.BASE_URL;
    private static RetrofitClient instance = null;
    private final Retrofit retrofit;

    private RetrofitClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("X-API-KEY", SpoonacularController.apiKey)
                            .build();
                    return chain.proceed(request);
                }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public SpoonacularController getSpoonacularController() {
        return retrofit.create(SpoonacularController.class);
    }
}
