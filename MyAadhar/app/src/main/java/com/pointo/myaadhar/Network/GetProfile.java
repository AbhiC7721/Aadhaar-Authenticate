package com.pointo.myaadhar.Network;

import android.util.Log;

import com.pointo.myaadhar.Callbacks.CaptchaCallback;
import com.pointo.myaadhar.Callbacks.KYCCallback;
import com.pointo.myaadhar.Callbacks.OTPCallback;
import com.pointo.myaadhar.Data.Config;
import com.pointo.myaadhar.Models.CaptchaRequestModel;
import com.pointo.myaadhar.Models.CaptchaResponseModel;
import com.pointo.myaadhar.Models.GetEKYCRequestModel;
import com.pointo.myaadhar.Models.GetOTPRequestModel;
import com.pointo.myaadhar.Models.GetOTPResponseModel;
import com.pointo.myaadhar.Models.GetProfileResponseModel;
import com.pointo.myaadhar.Models.ProfileFetch.KycRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class GetProfile {

    public static void getResponse(GetEKYCRequestModel requestModel, KYCCallback kycCallback){

        Retrofit retrofit = RetrofitClientBuilder.getInstance().build();

        Call<GetProfileResponseModel> call = retrofit.create(Api.class).getKYC(requestModel);

        call.enqueue(new Callback<GetProfileResponseModel>() {
            @Override
            public void onResponse(Call<GetProfileResponseModel> call, retrofit2.Response<GetProfileResponseModel> response) {

                try {
                    if (response.body() != null) {
                        Log.d("status", "kyc : --> "+response.body().toString());
                        kycCallback.onResponseRetrieved(response.body());
                    }
                    else  Log.d("status", "kyc = null :  Server error occurred -> "+response.errorBody().toString());

                } catch (Exception e) {
                    Log.d("status", "Failed to fetch KYC : "+e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetProfileResponseModel> call, Throwable t) {
                Log.d("status", "onFailure: Failed to fetch KYC : "+t.getMessage());
                t.printStackTrace();
                call.cancel();
            }
        });
    }
}