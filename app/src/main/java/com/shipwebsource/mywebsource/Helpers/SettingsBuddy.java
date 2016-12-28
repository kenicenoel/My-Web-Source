package com.shipwebsource.mywebsource.Helpers;

import android.content.Context;
import android.content.SharedPreferences;


public class SettingsBuddy
{

    private static SettingsBuddy settingsBuddy;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String DEFAULT = "N/A";

    public static SettingsBuddy getInstance(Context context)
    {
        if (settingsBuddy == null)
        {
            settingsBuddy = new SettingsBuddy(context);
        }
        return settingsBuddy;
    }

    private final String TAG = SettingsBuddy.class.getSimpleName();
    private SettingsBuddy(Context c)
    {
        sharedPreferences = c.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

    }





    public void saveData(String key,String value)
    {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.commit();
    }

    public String getData(String key)
    {
        if (sharedPreferences!= null)
        {
            return sharedPreferences.getString(key, DEFAULT);
        }
        return DEFAULT;
    }




}
