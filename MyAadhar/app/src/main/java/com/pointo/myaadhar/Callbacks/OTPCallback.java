package com.pointo.myaadhar.Callbacks;

import com.pointo.myaadhar.Models.GetOTPResponseModel;

public interface OTPCallback {
    void onResponseRetrieved(GetOTPResponseModel otpResponseModel);
}
