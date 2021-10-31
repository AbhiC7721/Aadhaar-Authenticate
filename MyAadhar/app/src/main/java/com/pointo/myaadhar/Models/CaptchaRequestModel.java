package com.pointo.myaadhar.Models;

public class CaptchaRequestModel {
    public String langCode;
    public String captchaLength;
    public String captchaType;

    public CaptchaRequestModel(String langCode, String captchaLength, String captchaType) {
        this.langCode = langCode;
        this.captchaLength = captchaLength;
        this.captchaType = captchaType;
    }
}
