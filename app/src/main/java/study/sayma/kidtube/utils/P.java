package study.sayma.kidtube.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Sayma on 12/27/2017.
 */

public class P {
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;

    /**
     * Not allowing any instance of this class
     */
    private P() {
        // just blocking the constructor.
    }

    public static String getData(String key) {
        return prefs.getString(key, "");
    }

    public static void assurePrefNotNull(Context context) {
        if (prefs == null)
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefsEditor == null) {
            prefsEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefsEditor.apply();
        }
    }

    public static void setData(String key, String value) {
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public static void appendData(String key, String value) {
        prefsEditor.putString(key, prefs.getString(key, "") + value);
        prefsEditor.commit();
    }
}
