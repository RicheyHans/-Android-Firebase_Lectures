package com.example.mcbud.firebasechat_171103.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mcbud on 2017-11-03.
 */

public class PreferenceUtil {

    private static final String filename = "FirebaseChatting";

    private static SharedPreferences getPreference(Context context){
        return context.getSharedPreferences(filename, Context.MODE_PRIVATE);
    }

    public static void setValue(Context context, String key, String value){
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setValue(Context context, String key, long value){
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static String getStringValue(Context context, String key){
        return getPreference(context).getString(key, "");
    }

    public static long getLongValue(Context context, String key){
        return getPreference(context).getLong(key, 0);

    }
}