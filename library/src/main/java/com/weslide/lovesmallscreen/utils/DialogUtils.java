package com.weslide.lovesmallscreen.utils;

import android.content.Context;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;

import net.aixiaoping.library.R;

/**
 * Created by xu on 2016/7/30.
 * Dialog的各种实现方式
 */
public class DialogUtils {

    private DialogUtils() {
        /** cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

//    public static Dialog.Builder getBuilder(final Context context) {
//        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
//
//        Dialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
//            @Override
//            public void onPositiveActionClicked(DialogFragment fragment) {
//                Toast.makeText(context, "Agreed", Toast.LENGTH_SHORT).show();
//                super.onPositiveActionClicked(fragment);
//            }
//
//            @Override
//            public void onNegativeActionClicked(DialogFragment fragment) {
//                Toast.makeText(context, "Disagreed", Toast.LENGTH_SHORT).show();
//                super.onNegativeActionClicked(fragment);
//            }
//        };
//    }

    /**
     *
     * @param context
     * @param title
     * @param message
     */
    public static void showSimpleDialog(final Context context, String title, String message) {

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;

        Dialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                Toast.makeText(context, "Agreed", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(context, "Disagreed", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };
    }

}
