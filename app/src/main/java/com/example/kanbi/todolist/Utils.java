package com.example.kanbi.todolist;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by kanbi on 04/01/2018.
 */

public class Utils {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
