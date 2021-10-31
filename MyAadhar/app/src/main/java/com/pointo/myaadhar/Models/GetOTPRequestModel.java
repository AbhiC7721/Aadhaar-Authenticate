package com.pointo.myaadhar.Models;

public class GetOTPRequestModel {
    public String uidNumber;
    public String captchaTxnId;
    public String captchaValue;
    public String transactionId;

    public GetOTPRequestModel(String uidNumber, String captchaTxnId, String captchaValue, String transactionId) {
        this.uidNumber = uidNumber;
        this.captchaTxnId = captchaTxnId;
        this.captchaValue = captchaValue;
        this.transactionId = transactionId;
    }
}
