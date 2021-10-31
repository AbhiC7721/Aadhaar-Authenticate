package com.pointo.myaadhar.Network;

import android.util.Log;

import com.pointo.myaadhar.Callbacks.CaptchaCallback;
import com.pointo.myaadhar.Callbacks.OTPCallback;
import com.pointo.myaadhar.Models.CaptchaRequestModel;
import com.pointo.myaadhar.Models.CaptchaResponseModel;
import com.pointo.myaadhar.Models.GetOTPRequestModel;
import com.pointo.myaadhar.Models.GetOTPResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class GetOTP {

    public static void getResponse(GetOTPRequestModel requestModel, OTPCallback otpCallback){

        Retrofit retrofit = RetrofitClientBuilder.getInstance().build();

        Call<GetOTPResponseModel> call = retrofit.create(Api.class).getOTP(requestModel);

        call.enqueue(new Callback<GetOTPResponseModel>() {
            @Override
            public void onResponse(Call<GetOTPResponseModel> call, retrofit2.Response<GetOTPResponseModel> response) {

                try {
                    if (response.body() != null) {
                        Log.d("status", "getOTP : --> "+response.body().status.toString());
                        otpCallback.onResponseRetrieved(response.body());
                    }
                    else  Log.d("status", "otp = null :  Server error occurred -> "+response.errorBody().toString());

                } catch (Exception e) {
                    Log.d("status", "Failed to fetch OTP : "+e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetOTPResponseModel> call, Throwable t) {
                Log.d("status", "onFailure: Failed to fetch OTP : "+t.getMessage());
                t.printStackTrace();
                call.cancel();
            }
        });
    }
}