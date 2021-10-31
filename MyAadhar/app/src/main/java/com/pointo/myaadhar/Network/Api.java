package com.pointo.myaadhar.Network;

import com.pointo.myaadhar.Models.CaptchaRequestModel;
import com.pointo.myaadhar.Models.CaptchaResponseModel;
import com.pointo.myaadhar.Models.GetEKYCRequestModel;
import com.pointo.myaadhar.Models.GetOTPRequestModel;
import com.pointo.myaadhar.Models.GetOTPResponseModel;
import com.pointo.myaadhar.Models.GetProfileResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {

    @POST("/unifiedAppAuthService/api/v2/get/captcha")
    Call<CaptchaResponseModel> getCaptcha(@Body CaptchaRequestModel requestModel);


    @POST("/unifiedAppAuthService/api/v2/generate/aadhaar/otp")
    Call<GetOTPResponseModel> getOTP(@Body GetOTPRequestModel otpRequestModel);

    @POST("/onlineekyc/getEkyc/")
    Call<GetProfileResponseModel> getKYC(@Body GetEKYCRequestModel requestModel);
}
