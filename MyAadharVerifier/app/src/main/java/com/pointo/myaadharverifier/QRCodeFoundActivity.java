package com.pointo.myaadharverifier;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pointo.myaadharverifier.databinding.ActivityQrcodeFoundBinding;

public class QRCodeFoundActivity extends AppCompatActivity {

    private TextView name;
    private TextView tvName;
    private TextView phoneNumber;
    private TextView tvPhoneNumber;
    private TextView address;
    private TextView tvAddress;
    private TextView gender;
    private TextView tvGender;
    private TextView dob;
    private TextView tvDob;

    private String qrResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_found);

        qrResult = getIntent().getStringExtra("qr");
        initViews();
    }

    private void initViews(){
        name = findViewById(R.id.name);
        tvName = findViewById(R.id.tvName);

        phoneNumber = findViewById(R.id.phoneNumber);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);

        address = findViewById(R.id.address);
        tvAddress = findViewById(R.id.tvAddress);

        gender = findViewById(R.id.gender);
        tvGender = findViewById(R.id.tvGender);

        dob = findViewById(R.id.dob);
        tvDob = findViewById(R.id.tvDob);
    }

    private void populateValues(){
        UserModel userModel = GsonUtil.getInstance().fromJson(qrResult, UserModel.class);

        String userName = userModel.name;
        String userAddress = userModel.address;
        String userPhoneNumber = userModel.phoneNumber;
        String userGender = userModel.gender;
        String userDob = userModel.dob;

        if(userName==null || userName.isEmpty()){
            name.setVisibility(View.GONE);
            tvName.setVisibility(View.GONE);
        }
        if(userAddress==null || userAddress.isEmpty()){
            address.setVisibility(View.GONE);
            tvAddress.setVisibility(View.GONE);
        }
        if(userPhoneNumber==null || userPhoneNumber.isEmpty()){
            phoneNumber.setVisibility(View.GONE);
            tvPhoneNumber.setVisibility(View.GONE);
        }
        if(userGender==null || userGender.isEmpty()){
            gender.setVisibility(View.GONE);
            tvGender.setVisibility(View.GONE);
        }
        if(userDob==null || userDob.isEmpty()) {
            dob.setVisibility(View.GONE);
            tvDob.setVisibility(View.GONE);
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateValues();
    }
}
