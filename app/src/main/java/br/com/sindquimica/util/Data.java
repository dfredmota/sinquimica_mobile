package br.com.sindquimica.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by fred on 12/01/17.
 */

public class Data {


    public static void saveToken(SharedPreferences shared, String token){

        SharedPreferences.Editor editor = shared.edit();

        // Save to SharedPreferences
        editor.putString("token_app", token);
        editor.apply();

    }

    public static String getToken(SharedPreferences shared){

        return shared.getString("token_app", null);
    }
}
