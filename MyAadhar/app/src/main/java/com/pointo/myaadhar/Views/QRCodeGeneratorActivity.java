package com.pointo.myaadhar.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.pointo.myaadhar.Data.Config;
import com.pointo.myaadhar.Models.User;
import com.pointo.myaadhar.R;
import com.pointo.myaadhar.Utils.QRCodeGenerator;
import com.pointo.myaadhar.Utils.SharedPreferenceUtil;
import com.suke.widget.SwitchButton;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class QRCodeGeneratorActivity extends AppCompatActivity{

    private ImageView ivQRCode;
    private SwitchButton switchName;
    private SwitchButton switchPhoneNumber;
    private SwitchButton switchPhoto;
    private SwitchButton switchAddress;
    private SwitchButton switchGender;
    private SwitchButton switchDob;

    private CoordinatorLayout coordinatorLayout;
    private ConstraintLayout constraintLayoutBackground;
    private ConstraintLayout constraintLayoutForeground;
    private BottomSheetBehavior bottomSheetBehavior;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String name;
    private String phoneNumber;
    private String address;
    private String photo;
    private String dob;
    private String gender;
    private User user;

    private Gson gson;
    private Bitmap bitmap;
    private String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);

        initViews();
    }

    private void initViews() {
        ivQRCode = findViewById(R.id.ivQRCode);
        switchName = findViewById(R.id.switchName);
        switchPhoneNumber = findViewById(R.id.switchPhoneNumber);
        switchPhoto = findViewById(R.id.switchPhoto);
        switchAddress = findViewById(R.id.switchAddress);
        switchGender = findViewById(R.id.switchGender);
        switchDob = findViewById(R.id.switchDob);

        coordinatorLayout = findViewById(R.id.coordinateLayout);
        constraintLayoutBackground = findViewById(R.id.constraintLayoutBackground);
        constraintLayoutForeground = findViewById(R.id.constraintLayoutForeground);

        initUtils();
    }

    private void initUtils(){
        sharedPreferences = SharedPreferenceUtil.getInstance(this);
        editor = sharedPreferences.edit();

        bottomSheetBehavior = BottomSheetBehavior.from(constraintLayoutForeground);
        bottomSheetBehavior.setFitToContents(false);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
    }

    private void populateValues() {
        name = sharedPreferences.getString(Config.SHARED_PREF_NAME,"");
        phoneNumber = sharedPreferences.getString(Config.SHARED_PREF_PHONE_NUMBER,"");
        photo = sharedPreferences.getString(Config.SHARED_PREF_PHOTO,"");
        address = sharedPreferences.getString(Config.SHARED_PREF_ADDRESS,"");
        gender = sharedPreferences.getString(Config.SHARED_PREF_GENDER,"");
        dob = sharedPreferences.getString(Config.SHARED_PREF_DOB,"");

        user = new User("","","","","","");
        gson = new Gson();

        setSwitchListener(switchName);
        setSwitchListener(switchPhoneNumber);
        setSwitchListener(switchPhoto);
        setSwitchListener(switchAddress);
        setSwitchListener(switchDob);
        setSwitchListener(switchGender);

    }

    private void setSwitchListener(SwitchButton switchButton){
        switchButton.setOnCheckedChangeListener((view, isChecked) -> {

            switch(switchButton.getId()) {
                case R.id.switchName:
                    if (isChecked)  user.name = name;
                    else    user.name = "";

                    jsonString = gson.toJson(user);
                    bitmap = QRCodeGenerator.getInstance().generateQRCode(jsonString);

                    if(bitmap!=null)
                        ivQRCode.setImageBitmap(bitmap);

                    switchPhoto.setChecked(false);
                    break;

                case R.id.switchPhoneNumber:
                    if (isChecked)  user.phoneNumber = phoneNumber;
                    else    user.phoneNumber = "";

                    jsonString = gson.toJson(user);
                    bitmap = QRCodeGenerator.getInstance().generateQRCode(jsonString);

                    if(bitmap!=null)
                        ivQRCode.setImageBitmap(bitmap);

                    switchPhoto.setChecked(false);
                    break;

                case R.id.switchPhoto:
                    if (isChecked) {
                        String imageDataBytes = photo.substring(photo.indexOf(",")+1);
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
                        Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);

                        ivQRCode.setImageBitmap(bitmap);
                    }

                    else {
                        jsonString = gson.toJson(user);
                        bitmap = QRCodeGenerator.getInstance().generateQRCode(jsonString);

                        if(bitmap!=null)
                            ivQRCode.setImageBitmap(bitmap);

                        switchPhoto.setChecked(false);
                    }
                    break;

                case R.id.switchAddress:
                    if (isChecked)  user.address = address;
                    else    user.address = "";

                    jsonString = gson.toJson(user);
                    bitmap = QRCodeGenerator.getInstance().generateQRCode(jsonString);

                    if(bitmap!=null)
                        ivQRCode.setImageBitmap(bitmap);

                    switchPhoto.setChecked(false);
                    break;

                case R.id.switchDob:
                    if (isChecked)  user.dob = dob;
                    else    user.dob = "";

                    jsonString = gson.toJson(user);
                    bitmap = QRCodeGenerator.getInstance().generateQRCode(jsonString);

                    if(bitmap!=null)
                        ivQRCode.setImageBitmap(bitmap);

                    switchPhoto.setChecked(false);
                    break;

                case R.id.switchGender:
                    if (isChecked)  user.gender = gender;
                    else    user.gender = "";

                    jsonString = gson.toJson(user);
                    bitmap = QRCodeGenerator.getInstance().generateQRCode(jsonString);

                    if(bitmap!=null)
                        ivQRCode.setImageBitmap(bitmap);

                    switchPhoto.setChecked(false);
                    break;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateValues();

        switchName.setChecked(true);
        user.name = name;
        jsonString = gson.toJson(user);
        bitmap = QRCodeGenerator.getInstance().generateQRCode(jsonString);

        if(bitmap!=null)
            ivQRCode.setImageBitmap(bitmap);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
