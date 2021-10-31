package com.pointo.myaadhar.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pointo.myaadhar.Callbacks.CaptchaCallback;
import com.pointo.myaadhar.Callbacks.OTPCallback;
import com.pointo.myaadhar.Data.Config;
import com.pointo.myaadhar.Models.CaptchaRequestModel;
import com.pointo.myaadhar.Models.CaptchaResponseModel;
import com.pointo.myaadhar.Models.GetOTPRequestModel;
import com.pointo.myaadhar.Models.GetOTPResponseModel;
import com.pointo.myaadhar.Network.GetCaptcha;
import com.pointo.myaadhar.Network.GetOTP;
import com.pointo.myaadhar.R;
import com.pointo.myaadhar.Utils.SharedPreferenceUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

public class PhoneNumberFragment extends Fragment {

    private EditText etAadharNumber;
    private EditText etCaptcha;
    private ImageView ivCaptcha;
    private Button butProceed;

    private static String captchaTransactionId;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_number, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        etAadharNumber  = view.findViewById(R.id.etAadharNumber);
        etCaptcha  = view.findViewById(R.id.etCaptcha);
        ivCaptcha  = view.findViewById(R.id.ivCaptcha);
        butProceed = view.findViewById(R.id.butProceed);

        sharedPreferences = SharedPreferenceUtil.getInstance(getContext());
        editor = sharedPreferences.edit();
    }

    private void populateValues(){

        CaptchaRequestModel captchaRequestModel = new CaptchaRequestModel("en","3","2");
        GetCaptcha.getResponse(captchaRequestModel, new CaptchaCallback() {
            @Override
            public void onResponseRetrieved(CaptchaResponseModel captchaResponseModel) {
                String captcha64 = captchaResponseModel.captchaBase64String;
                captchaTransactionId = captchaResponseModel.captchaTxnId;

                String imageDataBytes = captcha64.substring(captcha64.indexOf(",")+1);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
                Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);

                ivCaptcha.setImageBitmap(bitmap);
            }
        });

        butProceed.setOnClickListener(v -> onButtonClick());
    }

    private void onButtonClick(){
        String aadharNumber = etAadharNumber.getText().toString();
        String captcha = etCaptcha.getText().toString();

        if(aadharNumber.isEmpty() || captcha.isEmpty()){
            Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String uuid = UUID.randomUUID().toString();
        Log.d("status","UUID -> "+uuid);

        GetOTPRequestModel otpRequestModel = new GetOTPRequestModel(Config.STAGING_UID,captchaTransactionId,captcha,"MYAADHAAR:"+uuid);
        GetOTP.getResponse(otpRequestModel, otpResponseModel -> {
            if(otpResponseModel.status.equals("Success")){

                editor.putString(Config.SHARED_PREF_TRANSACTION_ID,otpResponseModel.txnId);
                editor.apply();

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_left_to_right,R.anim.slide_right_to_left);
                fragmentTransaction.replace(R.id.frameLayout, new OTPVerificationFragment()).commit();
            }
            else
                Toast.makeText(getContext(), "Invalid Captcha!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        if(!sharedPreferences.getBoolean(Config.SHARED_PREF_IS_FIRST_TIME,true))
            getActivity().startActivity(new Intent(getContext(), QRCodeGeneratorActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        populateValues();
    }
}