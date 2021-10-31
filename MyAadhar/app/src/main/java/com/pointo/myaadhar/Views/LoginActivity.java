package com.pointo.myaadhar.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.pointo.myaadhar.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        populateValues();
    }

    private void initViews(){

    }

    private void populateValues(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new PhoneNumberFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}