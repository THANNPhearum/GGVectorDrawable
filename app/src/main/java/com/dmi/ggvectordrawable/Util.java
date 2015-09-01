package com.dmi.ggvectordrawable;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by thannphearum on 9/1/15.
 */
public class Util {


    public static int[] getResolution(WindowManager windowManager) {
        Display display = windowManager.getDefaultDisplay();

        if (Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            int[] sizes = {size.x, size.y};
            return sizes;
        } else {
            int[] sizes = {display.getWidth(), display.getHeight()};
            return sizes;
        }
    }

    public static String getDensityName(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        if (density <= 0.75f) {
            return "ldpi";
        } else if (density > 0.75f && density <= 1.0f) {
            return "mdpi";
        } else if (density > 1.0f && density <= 1.5f) {
            return "hdpi";
        } else if (density > 1.5f && density <= 2.0f) {
            return "xhdpi";
        } else if (density > 2.0f && density <= 3.0f) {
            return "xxhdpi";
        } else if (density > 3.0f && density <= 4.0f) {
            return "xxxhdpi";
        }
        return "error";
    }

    /**
     * Returns the consumer friendly device name
     */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        if (manufacturer.equalsIgnoreCase("HTC")) {
            // make sure "HTC" is fully capitalized.
            return "HTC " + model;
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

    private static int getDimenSize(Context context, int id) {
        return (int) (context.getResources().getDimension(id) / context.getResources().getDisplayMetrics().density);
    }


}
