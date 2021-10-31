package com.pointo.myaadhar.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pointo.myaadhar.Callbacks.KYCCallback;
import com.pointo.myaadhar.Data.Config;
import com.pointo.myaadhar.Models.GetEKYCRequestModel;
import com.pointo.myaadhar.Models.GetProfileResponseModel;
import com.pointo.myaadhar.Models.ProfileFetch.KYCModel;
import com.pointo.myaadhar.Models.ProfileFetch.KycRes;
import com.pointo.myaadhar.Models.ProfileFetch.Poa;
import com.pointo.myaadhar.Models.ProfileFetch.Poi;
import com.pointo.myaadhar.Models.ProfileFetch.UidData;
import com.pointo.myaadhar.Network.GetProfile;
import com.pointo.myaadhar.R;
import com.pointo.myaadhar.Utils.GsonUtil;
import com.pointo.myaadhar.Utils.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class OTPVerificationFragment extends Fragment {
    private EditText etOTP;
    private Button butProceed;

    private static String transactionId;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_o_t_p_verification, container, false);
        initViews(view);
        populateValues();
        return view;
    }

    private void initViews(View view) {
        etOTP = view.findViewById(R.id.etOTP);
        butProceed = view.findViewById(R.id.butLogin);

        sharedPreferences = SharedPreferenceUtil.getInstance(getContext());
        editor = sharedPreferences.edit();

        transactionId = sharedPreferences.getString(Config.SHARED_PREF_TRANSACTION_ID,"");
    }

    private void populateValues() {
        butProceed.setOnClickListener(v -> onButtonClick());
    }

   private void onButtonClick(){
       String otp = etOTP.getText().toString();

       if(otp.isEmpty()){
           Toast.makeText(getContext(), "OTP can not be empty", Toast.LENGTH_SHORT).show();
           return;
       }

       Log.d("status","TRANSACTION ID: "+transactionId);

       GetEKYCRequestModel requestModel = new GetEKYCRequestModel(Config.STAGING_UID,transactionId ,otp);
       GetProfile.getResponse(requestModel, profileResponseModel -> {
           if(profileResponseModel.status.equals("Y")){
                String kycXml = profileResponseModel.eKycString;
                if(kycXml!=null && !kycXml.isEmpty()){

                    try {
                        JSONObject json = XML.toJSONObject(kycXml);
                        String jsonString = json.get("KycRes").toString();
                        KycRes kycRes = GsonUtil.getInstance().fromJson(jsonString, KycRes.class);

                        Poi poi = kycRes.UidData.Poi;
                        Poa poa = kycRes.UidData.Poa;

                        String name = poi.name;
                        String gender = poi.gender;
                        String dob = poi.dob;
                        String phone = ""+poi.phone;
                        String photo = kycRes.UidData.Pht;
                        String address = poa.vtc + ", "+ poa.dist +", "+ poa.state + ", "+ poa.country + " "+poa.pc;

                        Log.d("status", "ADDRESS-->"+address);

                        editor.putString(Config.SHARED_PREF_NAME, name);
                        editor.putString(Config.SHARED_PREF_GENDER, gender);
                        editor.putString(Config.SHARED_PREF_DOB, dob);
                        editor.putString(Config.SHARED_PREF_PHONE_NUMBER, phone);
                        editor.putString(Config.SHARED_PREF_PHOTO, photo);
                        editor.putString(Config.SHARED_PREF_ADDRESS, address);

                        editor.putBoolean(Config.SHARED_PREF_IS_FIRST_TIME,false);
                        editor.apply();

                        getActivity().startActivity(new Intent(getContext(), QRCodeGeneratorActivity.class));
                    }
                    catch (JSONException e) {
                        Toast.makeText(getContext(), "Oops! Error occurred : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
           }
           else {
               Toast.makeText(getContext(), "Invalid OTP! Try again", Toast.LENGTH_SHORT).show();
           }
       });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}