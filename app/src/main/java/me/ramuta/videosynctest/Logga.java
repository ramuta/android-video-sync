package me.ramuta.videosynctest;

import android.util.Log;

/**
 * Created by ramuta on 18/11/2014.
 */
public class Logga {
    private static final boolean DEBUGGA = true;

    public static void i(final String message) {
        if(DEBUGGA) {
            final String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
            final String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            final int lineNumba = Thread.currentThread().getStackTrace()[3].getLineNumber();

            Log.i(className, "#" + lineNumba + " " + message);
        }
    }

    public static void e(final String message) {
        if(DEBUGGA) {
            final String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
            final String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            final int lineNumba = Thread.currentThread().getStackTrace()[3].getLineNumber();

            Log.e(className, "#" + lineNumba + " ERRA!!1 " + message);
        }
    }
}
