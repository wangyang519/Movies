package com.example.bawei.com.movies.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {

    //保存布尔值
    @SuppressLint("CommitPrefEdits")
    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("day092", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean(key, value);

        editor.commit();

    }

    //取值
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("day092", Context.MODE_PRIVATE);

        return sp.getBoolean(key, true);
    }
}
