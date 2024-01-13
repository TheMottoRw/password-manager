package com.example.password_manager.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Utils {
    private Context ctx;
    public Utils(Context context){
        ctx=context;
    }
    public void setField(String key,String val) {
            SharedPreferences.Editor sharedPreferences = ctx.getSharedPreferences("uinfo", ctx.MODE_PRIVATE).edit();
            sharedPreferences.putString(key, val);
            sharedPreferences.apply();
    }
    public String getField(String key) {
            SharedPreferences sharedPreferences = ctx.getSharedPreferences("uinfo", ctx.MODE_PRIVATE);
            return sharedPreferences.getString(key, "");
    }
    public static String currentDateTime(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDateTime.now().toString().replace("T"," ").substring(0,19);
        }
        return "";
    }

}
