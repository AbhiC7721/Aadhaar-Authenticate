package com.pointo.myaadhar.Callbacks;

import com.pointo.myaadhar.Models.CaptchaResponseModel;

import java.net.CacheResponse;

public interface CaptchaCallback {
    void onResponseRetrieved(CaptchaResponseModel cacheResponse);
}
