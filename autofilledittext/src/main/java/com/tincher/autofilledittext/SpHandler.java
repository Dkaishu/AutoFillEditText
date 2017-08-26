package com.tincher.autofilledittext;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/8/25.
 */

public class SpHandler {
    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        SharedPreferences sp = ctx.getSharedPreferences("autoFill",
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static void setBoolean(Context ctx, String key, boolean value) {
        SharedPreferences sp = ctx.getSharedPreferences("autoFill",
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }

    public static void setString(Context ctx, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences("autoFill",
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static String getString(Context ctx, String key, String defValue) {
        SharedPreferences sp = ctx.getSharedPreferences("autoFill",
                Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }
}
