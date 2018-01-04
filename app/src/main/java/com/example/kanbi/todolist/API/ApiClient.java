package com.example.kanbi.todolist.API;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by kanbi on 30/12/2017.
 */
// URL: https://timesheet-1172.appspot.com/2a00ea83/notes
public class ApiClient {
    public static final String TODO_URL = "https://timesheet-1172.appspot.com/";
    private static Retrofit retrofit = null;

    // Add the interceptor to OkHttpClient
   static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(
                    new Interceptor() {
                        @Override
                        public Response intercept(Interceptor.Chain chain) throws IOException {
                            Request original = chain.request();
                            // Request customization: add request headers
                            Request.Builder requestBuilder = original.newBuilder()
                                    .method(original.method(), original.body());

                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    })
            .build();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(TODO_URL)
                    .build();
        }
        return retrofit;
    }

}



