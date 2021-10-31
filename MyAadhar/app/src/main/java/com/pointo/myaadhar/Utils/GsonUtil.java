package com.pointo.myaadhar.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

    private static Gson gson;

    public static Gson getInstance(){               // singleton pattern
        if(gson==null)
            gson = new GsonBuilder().create();

        return gson;
    }
}