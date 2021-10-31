package com.pointo.myaadhar.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

    private static SharedPreferences sharedPreferences;

    public static SharedPreferences getInstance(Context context){
        if(sharedPreferences==null){
            String fileName = context.getPackageName()+".PREFERENCE_FILE_1";
            sharedPreferences = context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }
}