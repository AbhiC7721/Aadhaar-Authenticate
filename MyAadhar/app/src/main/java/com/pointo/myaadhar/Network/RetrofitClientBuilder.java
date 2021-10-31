package com.pointo.myaadhar.Network;

import com.pointo.myaadhar.Data.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientBuilder {
    private static Retrofit.Builder retrofitBuilder;

    public static Retrofit.Builder getInstance(){             //singleton class pattern

        if(retrofitBuilder==null){
            retrofitBuilder=new Retrofit.Builder()
                    .baseUrl(Config.SERVER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
        }
        return retrofitBuilder;
    }
}
