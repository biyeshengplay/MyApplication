package com.lee.myapplication;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;

/**
 * Created by alvinlee on 16/6/21.
 */
public class Util {

    private static final String TAG = Util.class.getSimpleName();

    private static DisplayMetrics DISPLAY_METRICES = null;

    public static DisplayMetrics getDisplayMetrics(Context pContext) {
        if (DISPLAY_METRICES == null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager wm = (WindowManager) pContext.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(displayMetrics);
            DISPLAY_METRICES = displayMetrics;
        }
        return DISPLAY_METRICES;
    }

    public static String getActionName(MotionEvent event) {
        String name = "";
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            name = "ACTION_DOWN";
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            name = "ACTION_UP";
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            name = "ACTION_MOVE";
        }
        return name;
    }

    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

}
