package study.sayma.kidtube.utils;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import study.sayma.kidtube.R;

/**
 * Created by touhidroid on 4/21/16.
 *
 * @author touhidroid
 */
public class U {

    private static final String TAG = U.class.getSimpleName();
    private static final String KEY_NOTIF_GRP_SN = "sn_gcm_notifs_grp";

    // private static RegimeApp regimeAppInstance;

    /**
     * Not allowing any instance of this class
     */
    private U() {
        // just blocking the constructor.
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * @return Screen width or height, depending on which is minimum
     */
    public static int getScreenMinHW(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        return w < h ? w : h;
    }

    /*public static boolean isGooglePlayServiceAvailable(BaseActivity activity) {
        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
        int status = availability.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (availability.isUserResolvableError(status)) {
                availability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }*/

    public static String getDeviceDetailsJSONString(Context ctx) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("os", Build.VERSION.SDK_INT);
            jo.put("model", Build.MODEL);
            jo.put("manufacturer", Build.MANUFACTURER);
            jo.put("ip", getDeviceIp(true));
            // jo.put("imei", getDeviceIMEI(ctx));
            jo.put("mac", getDeviceMAC(ctx));
            jo.put("dpi", U.getDeviceDpi(ctx));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo.toString();
    }

    public static float getDeviceDpi(Context context) {
        return context.getResources().getDisplayMetrics().density * 160.0f;
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    @SuppressLint("DefaultLocale")
    public static String getDeviceIp(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            // for now eat exceptions
        }
        return "";
    }

    public static String getDeviceMAC(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        @SuppressLint("MissingPermission") WifiInfo wInfo = wifiManager.getConnectionInfo();
        return wInfo.getMacAddress();
    }

    public static boolean isGPSProviderEnabled(LocationManager locationManager) {
        // getting GPS status
        return locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean isNetProviderEnabled(LocationManager locationManager) {
        // getting network status
        return locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static boolean isDevicePortrait(Resources res) {
        return res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public static boolean isAboveOreo() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O);
    }

    public static boolean isNetConnected(Context context) {
        if (context == null)
            return false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo.isConnected();
    }

    public static void saveBitmapToFile(Bitmap bmpToSave, File directory,
                                        String fileName) {
        if (!directory.exists())
            directory.mkdirs();
        File file = new File(directory, fileName);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            bmpToSave.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getResId(String drawableName) {
        try {
            Class<R.drawable> res = R.drawable.class;
            Field field = res.getField(drawableName);
            return field.getInt(null);
        } catch (Exception e) {
            Log.e("COUNTRYPICKER", "Failure to get drawable id.", e);
            return -1;
        }
    }

    @SuppressLint("MissingPermission")
    public static String getFirstSimCardNumber(Context tContext) {
        try {
            TelephonyManager tm = (TelephonyManager) tContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getLine1Number();
        } catch (Exception e) {
            Log.e(TAG,
                    "getFirstSimCardNumber : Could not retrieve the SIM card number");
            return "";
        }
    }

    // region Validation Section
    public static boolean isEmailValid(String email) {
        return email.length() > 3 && email.indexOf('@') > 0 && isValidEmailAddress(email);
    }

    private static boolean isValidEmailAddress(String email) {
        return (!TextUtils.isEmpty(email))
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    //endregion

    public static boolean isPhoneNumberValid(String phoneNo) {
        return ((
                (phoneNo.startsWith("880") && phoneNo.length() > 11)
                        || (!phoneNo.startsWith("880") && phoneNo.length() > 5))
                && phoneNo.matches("\\d+"));
    }

    public static void sendEmail(Context context, String[] mailTos, String subject, String body) {
        sendEmail(context, mailTos, subject, body, "plain/text", "Send eMail");
    }

    public static void sendEmail(Context context, String[] mailTos, String subject, String body,
                                 String type, String chooserTitle) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(type);
        intent.putExtra(Intent.EXTRA_EMAIL, mailTos);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(intent, chooserTitle));
    }

    public static void dialToCall(Context context, String phoneNumber) {
        if (phoneNumber.contains(",")) {
            showCallChooser(context, phoneNumber);
        } else {
            Uri callUri = Uri.parse("tel:" + phoneNumber);
            context.startActivity(new Intent(Intent.ACTION_DIAL, callUri));
        }
    }

    private static void showCallChooser(final Context context, String phoneNumber) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setIcon(R.mipmap.ic_launcher);
        builderSingle.setTitle("Select to Dial:");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,
                android.R.layout.select_dialog_item);
        StringTokenizer tokens = new StringTokenizer(phoneNumber, ",");
        while (tokens.hasMoreTokens()) {
            String num = tokens.nextToken().trim();
            if (!num.toLowerCase().startsWith("ext:"))
                arrayAdapter.add(num);
        }

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialToCall(context, arrayAdapter.getItem(which));
            }
        });
        builderSingle.show();
    }

    public static String parseSecondsToTimeStr(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return (minutes + ":" + secs);
    }

    public static String getFormattedSize(long sizeInBytes) {
        String ret = "";
        if (sizeInBytes < 1024) // Less than 1KB
            ret = sizeInBytes + "B";
        else if (sizeInBytes < (1024 * 1024)) // less than 1MB
            ret = String.format(Locale.US, "%.2fKB", ((float) sizeInBytes / 1024.0));
        else if (sizeInBytes < (1024 * 1024 * 1024)) // less than 1GB
            ret = String.format(Locale.US, "%.2fMB", ((float) sizeInBytes / (1024.0 * 1024.0)));
        else
            ret = String.format(Locale.US, "%.3fGB", ((float) sizeInBytes / (1024.0 * 1024.0 * 1024.0)));
        return ret;
    }

    public static void startActivityWithBgTransition(Activity activity,
                                                     Class<? extends Activity> toClass) {
        Intent it = new Intent(activity, toClass);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        /*View bg = activity.findViewById(R.id.bg);
        if (bg != null) {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity,
                            Pair.create(bg, AppConstants.KEY_BG_TRANSITION));
            ActivityCompat.startActivity(activity, it, options.toBundle());
        } else*/
        activity.startActivity(it);
    }

    public static void startActivityWithBgTransition(Activity activity, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        /*View bg = activity.findViewById(R.id.bg);
        if (bg != null) {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity,
                            Pair.create(bg, AppConstants.KEY_BG_TRANSITION));
            ActivityCompat.startActivity(activity, intent, options.toBundle());
        } else*/
        activity.startActivity(intent);
    }

    public static void startActivityWithBgTransition(Activity activity, Intent intent,
                                                     Pair<View, String>... pairs) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, pairs);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static int getCurSDK() {
        return Build.VERSION.SDK_INT;
    }

    public static boolean isAboveLollipop() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }

    public static int getColor(Context context, int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return context.getResources().getColor(colorResId, null);
        else
            return context.getResources().getColor(colorResId);
    }

    public static int getDrawerWidth(Context context) {
        return (int) (getScreenWidth(context) * 0.70);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static String get3LetterFormattedCount(int count) {
        String ret = count + "";
        if (count > 999999999)
            ret = (count / 1000000000) + "b";
        else if (count > 999999)
            ret = (count / 1000000) + "m";
        else if (count > 999)
            ret = (count / 1000) + "k";
        // else if (count > 99)
        // ret = (count / 100) + "h";
        return ret;
    }

    /**
     * Default return is an empty JSONArray [ new JSONArray() ]
     */
    public static JSONArray getJSONArrayJ(JSONObject jo, String key) {
        try {
            if (jo != null && jo.has(key))
                if (jo.get(key) != null)
                    return jo.getJSONArray(key);
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    /**
     * Default return is an empty JSONObject [ new JSONObject() ]
     */
    public static JSONObject getJSONObjectJ(JSONObject jo, String key) {
        try {
            if (jo != null && jo.has(key))
                if (jo.get(key) != null)
                    return jo.getJSONObject(key);
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public static JSONObject getJSONObjectJ(JSONObject jo, String key, JSONObject defRet) {
        try {
            if (jo != null && jo.has(key))
                if (jo.get(key) != null)
                    return jo.getJSONObject(key);
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return defRet;
    }

    /**
     * @param defMsg Default return if there is null/empty string inside JSONObject under the key
     */
    public static String getStringJ(JSONObject jo, String key, String defMsg) {
        if (jo == null)
            return defMsg;
        String s = U.getStringJ(jo, key);
        if (s == null || s.length() < 1 || s.trim().toLowerCase().equals("null"))
            return defMsg;
        else
            return s;
    }

    /**
     * Default return empty string("")
     */
    public static String getStringJ(JSONObject jo, String key) {
        try {
            if (jo != null && jo.has(key))
                if (jo.get(key) != null)
                    return jo.getString(key);
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Default return is 0.0
     */
    public static double getDoubleJ(JSONObject jo, String key) {
        try {
            if (jo != null && jo.has(key))
                if (jo.get(key) != null)
                    return jo.getDouble(key);
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * @param defRetVal The default return value in case of failed parsing
     */
    public static double getDoubleJ(JSONObject jo, String key, double defRetVal) {
        try {
            if (jo != null && jo.has(key))
                if (jo.get(key) != null)
                    return jo.getDouble(key);
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return defRetVal;
    }

    /**
     * Default return is 0
     */
    public static int getIntJ(JSONObject jo, String key) {
        if (jo == null)
            return 0;
        try {
            if (jo.has(key))
                if (jo.get(key) != null)
                    return Integer.parseInt(jo.get(key) + "");
        } catch (JSONException | NumberFormatException | NullPointerException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param defaultRetInt The default return value in case of failed parsing
     */
    public static int getIntJ(JSONObject jo, String key, int defaultRetInt) {
        if (jo == null)
            return 0;
        try {
            if (jo.has(key))
                if (jo.get(key) != null)
                    return Integer.parseInt(jo.get(key) + "");
        } catch (JSONException | NumberFormatException | NullPointerException e) {
            e.printStackTrace();
        }
        return defaultRetInt;
    }

    /**
     * Default return is false
     */
    public static boolean getBooleanJ(JSONObject jo, String key) {
        if (jo == null)
            return false;
        try {
            if (jo.has(key))
                if (jo.get(key) != null)
                    return jo.getBoolean(key);
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param defRet Default return in case of no value under the specified key.
     */
    public static boolean getBooleanJ(JSONObject jo, String key, boolean defRet) {
        try {
            if (jo != null && jo.has(key))
                if (jo.get(key) != null)
                    return jo.getBoolean(key);
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return defRet;
    }

    /**
     * Default return is an empty object [ new Object() ]
     */
    public static Object getJ(JSONObject jo, String key) {
        try {
            if (jo != null && jo.has(key))
                if (jo.get(key) != null)
                    return jo.get(key);
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return new Object();
    }

    public static ArrayList<String> parseStringList(JSONArray ja) {
        ArrayList<String> strList = new ArrayList<>();

        int sz = ja.length();
        for (int i = 0; i < sz; i++) {
            try {
                strList.add(ja.getString(i));
            } catch (JSONException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        return strList;
    }

    //region Time-related calculations
    public static CharSequence getTimeElapsedAfterPost(String updatedAt) {
        CharSequence result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                Date mDate = sdf.parse(updatedAt);
                long tms = mDate.getTime();
                result = DateUtils.getRelativeTimeSpanString(tms,
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            Log.e("getTimeElapsedAfterPost", "Date format can't be parsed: " + updatedAt, e);
        }
        return result;
    }

    public static String getSmallDateFormat(String dateTimeAt) {
        try {
            // Thu, Feb 2, 2017 12:00 AM
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm aaa", Locale.ENGLISH);
            try {
                Date mDate = sdf.parse(dateTimeAt);
                SimpleDateFormat sdfDate = new SimpleDateFormat("d MMM, yy", Locale.ENGLISH);
                return sdfDate.format(mDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            Log.e("getTimeElapsedAfterPost", "Date format can't be parsed: " + dateTimeAt, e);
        }
        return "";
    }

    public static long getTimeStamp(String formattedTimeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        long timeStamp = Long.MIN_VALUE;
        try {
            Date mDate = sdf.parse(formattedTimeStr);
            timeStamp = mDate.getTime();
        } catch (ParseException e) {
            Log.e("U.getTimeStamp()",
                    "Failed to format the string: " + formattedTimeStr, e);
        }
        return timeStamp;
    }

    /**
     * Default return : now
     */
    public static Date parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        long timeStamp = Long.MIN_VALUE;
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String readDate(Date date) {
        SimpleDateFormat toDateFormat = new SimpleDateFormat("d MMM, yyyy", Locale.ENGLISH);
        return toDateFormat.format(date);
    }

    public static String readDate(Calendar calendar) {
        SimpleDateFormat toDateFormat = new SimpleDateFormat("d MMM, yyyy", Locale.ENGLISH);
        return toDateFormat.format(calendar.getTime());
    }

    public static String reformatDate(String dateStr, String fromFormat, String toFormat) {
        SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromFormat, Locale.ENGLISH);
        try {
            Date formattedDate = fromDateFormat.parse(dateStr);
            SimpleDateFormat toDateFormat = new SimpleDateFormat(toFormat, Locale.ENGLISH);
            return toDateFormat.format(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date now = new Date();
        return sdfDate.format(now);
    }

    public static Map<TimeUnit, Long> computeDiff(Date date1, Date date2) {
        long diffInMillis = date2.getTime() - date1.getTime();
        List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);
        Map<TimeUnit, Long> result = new LinkedHashMap<>();
        long millisRest = diffInMillis;
        for (TimeUnit unit : units) {
            long diff = unit.convert(millisRest, TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            millisRest = millisRest - diffInMilliesForUnit;
            result.put(unit, diff);
        }
        return result;
    }

    public static int computeDiffInDays(Date d1, Date d2) {
        Log.d("DATE", "computeDiffInDays : d1=" + d1.toString() + ", d2=" + d2.toString());
        long a = (d1.getTime() - d2.getTime())
                / (1000 * 60 * 60 * 24);
        if (a < 0) a = a * (-1);
        return Math.round(a);
    }

    public static String getTimeElapsedCurrent() {
        return (String) getTimeElapsedAfterPost(getCurrentTimeStamp());
    }

    public static String getSmallFormattedTime(int timeInSecs) {
        String ret = "";

        if (timeInSecs < 0)
            timeInSecs *= -1;

        if ((timeInSecs / 60) > 0) {
            int x = timeInSecs / 60;
            if (x < 10)
                ret += "0" + x;
            else
                ret += "" + x;
            timeInSecs %= 60;
        } else
            ret += "00";
        if (timeInSecs >= 0) {
            if (timeInSecs < 10)
                ret += "  :  0" + timeInSecs;
            else
                ret += "  :  " + timeInSecs;
        }

        return ret;
    }
    //endregion

    public static Drawable getDrawable(Context context, int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return context.getResources().getDrawable(resId, null);
        else
            return context.getResources().getDrawable(resId);
    }

    public static void logHashKey(Activity activity) {
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    "org.durbinbd.DurbinStudent", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String k = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("KeyHash:", k);
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            Log.e(TAG, "showHashKey()", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //region Animation
    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            public boolean willChangeBounds() {
                return true;
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }


        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)
                (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapseWithout1dp(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms upto 1dp remains
        a.setDuration((int)
                (initialHeight / v.getContext().getResources().getDisplayMetrics().density) - 1);
        v.startAnimation(a);
    }

    public static void animateTextView(final TextView textview, int initialValue, int finalValue) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(1500);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textview.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();

    }
    //endregion

    //region Picture-utilities

    public static boolean isSDCardMounted() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    public static void copyI2OStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    public static String getPathFromURI(Context context, Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULL-POINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        } else
            return uri.getPath();
    }

    public static int getCorrectionAngleForCam(File picFile) throws IOException {
        ExifInterface exif = new ExifInterface(picFile.getPath());
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        int angle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            angle = 90;
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            angle = 180;
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            angle = 270;
        }
        Log.d(TAG, "angle = " + angle);
        return angle;
    }

    public static Bitmap decodeFile(File picFile, int requiredSize) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(picFile);
            BitmapFactory.decodeStream(stream1, null, o);
            stream1.close();

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;

            while (true) {
                if (width_tmp / 2 < requiredSize
                        || height_tmp / 2 < requiredSize)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            Log.i("SCALE", "scale = " + scale);

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(picFile);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //endregion

    // @Deprecated
    public static void setLeftImageSpannable(Context context, TextView tv,
                                             int imgResId, String str) {
        SpannableString ss = new SpannableString(str);
        Log.e(TAG, "Text Span : " + ss.toString());
        // Drawable d = getDrawable(context, imgResId);
        // d.setBounds(0, 0, tv.getLineHeight(), tv.getLineHeight());
        // d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        // ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        ImageSpan imgSpan = new ImageSpan(context, imgResId);
        ss.setSpan(imgSpan, 0, 1, 0); // Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        // FIXME Texts are lost after setting the image-span;
        Log.e(TAG, "Final image-attached Span : " + ss.toString());
        tv.setText(ss);

        /*SpannableString spannable = new SpannableString(str);
        Drawable myIcon = getDrawable(context, imgResId);
        myIcon.setBounds(0, 0, myIcon.getIntrinsicWidth(), myIcon.getIntrinsicHeight());
        spannable.setSpan(new ImageSpan(myIcon, ImageSpan.ALIGN_BASELINE),
                matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(spannable, TextView.BufferType.SPANNABLE);*/
    }

    public static void setLeftImageSpannable(Context context, TextView tv,
                                             int imgResId, SpannableStringBuilder str) {
        SpannableString ss = new SpannableString(str);
        Log.e(TAG, "Text Span : " + ss.toString());
        ImageSpan imgSpan = new ImageSpan(context, imgResId);
        ss.setSpan(imgSpan, 0, 1, 0);
        Log.e(TAG, "Final image-attached Span : " + ss.toString());
        tv.setText(ss);
    }

    public static String getImageBase64Str(Bitmap bitmap) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        return Base64.encodeToString(ba, Base64.DEFAULT);
    }

    public static SpannableString getUnderlinedString(String string) {
        SpannableString content = new SpannableString(string);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;
    }

    public static void gotoPlayStorePage(Context context) {
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(
                    new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(
                    new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void fillImageWidth(Context context, Bitmap bitmap, ImageView iv) {
        int imgHeight = bitmap.getHeight();
        int imgWidth = bitmap.getWidth();
        int sw = (int) (U.getScreenWidth(context));// * 0.80);
        int h = (imgHeight * sw) / imgWidth;
        ViewGroup.LayoutParams lp = iv.getLayoutParams();
        lp.height = h;
        lp.width = sw;
        iv.setLayoutParams(lp);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboardFromFragment(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getHTMLStr(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return Html.fromHtml(source, Html.FROM_HTML_OPTION_USE_CSS_COLORS).toString();
        else
            return Html.fromHtml(source).toString();
    }

    public static SpannableStringBuilder getAlteredBoldStr(String[] alteredStrArr,
                                                           boolean isFirstPartBold) {
        SpannableStringBuilder str = new SpannableStringBuilder();
        for (int i = 0, l = alteredStrArr.length; i < l; i++) {
            String s = alteredStrArr[i];
            int pl = str.length();
            if (i > 0) str.append(" ");
            str.append(s);
            if ((isFirstPartBold && i % 2 == 0)
                    || (!isFirstPartBold && i % 2 != 0))
                str.setSpan(new StyleSpan(Typeface.BOLD),
                        pl, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return str;
    }

    public static void copyToClipboard(Context context, String textToSopy) {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard =
                    (android.text.ClipboardManager)
                            context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(textToSopy);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText(
                    "text label", textToSopy);
            clipboard.setPrimaryClip(clip);
        }
        // D.showToastLong(context, "Copied");
    }

    public static int parseInt(String numStr, int defValue) {
        try {
            return Integer.parseInt(numStr);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            return defValue;
        }
    }

    public static boolean isUserIdCorrect(EditText etName) {
        if (etName == null)
            return false;
        String userName = etName.getText().toString();
        if (userName.contains(" "))
            etName.setError("User-Id can't have any space.");
        else if (!userName.matches("[a-zA-Z0-9]*"))
            etName.setError("Only alphabets (a-z & A-Z) and numbers (0-9) are allowed.");
        else
            return true;
        etName.requestFocus();
        return false;
    }

    public static void gotoWebsite(Context context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static void launchUrl(Context context, String url) {
        launchUri(context, Uri.parse(url));
    }

    public static void launchUri(Context context, Uri uri) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}
    /*public boolean isPlayServiceAvailable(Context context) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }*/
/*
    public static void setupSpringSystem(final View springedView, final SimplestCallback callback) {
        final SpringSystem mSpringSystem = SpringSystem.create();
        final Spring mSpring = mSpringSystem.createSpring();
        if (mSpring == null) return;
        mSpring.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                springedView.setScaleX(scale);
                springedView.setScaleY(scale);
            }
            @Override
            public void onSpringAtRest(Spring spring) {
            }
            @Override
            public void onSpringActivate(Spring spring) {
            }
            @Override
            public void onSpringEndStateChange(Spring spring) {
            }
        });
        SpringConfig config = new SpringConfig(AppConstants.REBOUND_TENSION, AppConstants.REBOUND_DAMPER);
        mSpring.setSpringConfig(config);
        springedView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mSpring == null)
                            return false;
                        mSpring.setEndValue(1f);
                        callback.justSayMe();
                        springedView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (springedView == null || mSpring == null)
                                    return;
                                if (mSpring.getEndValue() > 0.5f)
                                    mSpring.setEndValue(0f);
                            }
                        }, 750);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mSpring.setEndValue(0f);
                        return true;
                }
                return false;
            }
        });
    }*/
