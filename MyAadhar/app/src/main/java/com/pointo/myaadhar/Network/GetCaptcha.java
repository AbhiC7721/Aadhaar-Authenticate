package com.pointo.myaadhar.Network;

import android.util.Log;

import com.pointo.myaadhar.Callbacks.CaptchaCallback;
import com.pointo.myaadhar.Models.CaptchaRequestModel;
import com.pointo.myaadhar.Models.CaptchaResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class GetCaptcha {

    public static void getResponse(CaptchaRequestModel requestModel, CaptchaCallback captchaCallback){

            Retrofit retrofit = RetrofitClientBuilder.getInstance().build();

            Call<CaptchaResponseModel> call = retrofit.create(Api.class).getCaptcha(requestModel);

            call.enqueue(new Callback<CaptchaResponseModel>() {
                @Override
                public void onResponse(Call<CaptchaResponseModel> call, retrofit2.Response<CaptchaResponseModel> response) {

                    try {
                        if (response.body() != null) {
                            Log.d("status", "getCaptcha : --> "+response.body().toString());
                            captchaCallback.onResponseRetrieved(response.body());
                        }
                        else  Log.d("status", "captcha = null :  Server error occurred -> "+response.errorBody().toString());

                    } catch (Exception e) {
                        Log.d("status", "Failed to fetch captcha : "+e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<CaptchaResponseModel> call, Throwable t) {
                    Log.d("status", "onFailure: Failed to fetch captcha : "+t.getMessage());
                    t.printStackTrace();
                    call.cancel();
                }
        });
    }
}