package com.pointo.myaadhar.Callbacks;

import com.pointo.myaadhar.Models.GetProfileResponseModel;
import com.pointo.myaadhar.Models.ProfileFetch.KycRes;

public interface KYCCallback {
    void onResponseRetrieved(GetProfileResponseModel profileResponseModel);
}
