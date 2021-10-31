package com.pointo.myaadhar.Models;

public class GetEKYCRequestModel {
    public String vid;
    public String txnId;
    public String otp;

    public GetEKYCRequestModel(String vid, String txnId, String otp) {
        this.vid = vid;
        this.txnId = txnId;
        this.otp = otp;
    }
}
