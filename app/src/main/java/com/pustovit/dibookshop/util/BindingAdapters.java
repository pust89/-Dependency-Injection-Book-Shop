package com.pustovit.dibookshop.util;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

public class BindingAdapters {
    private static final String TAG = "tag";

    private BindingAdapters() {
    }

    @BindingAdapter({"textPriceItemBook"})
    public static void setTextPrice(TextView textView, double price) {
        textView.setText(String.format(Locale.getDefault(), "%.2f $", price));
    }

    @BindingAdapter(value = {"textPriceAddEdit"})
    public static void setTextPriceAddEdit(EditText editText, double price) {
        Log.d(TAG, "++++++++++++++++++++setTextPriceAddEdit: start with param price=" + price);

        String strField = editText.getText().toString();

        if (!TextUtils.isEmpty(strField)) {
            double oldValue = Double.parseDouble(strField);
            if (price != oldValue) {
                editText.setText(String.valueOf(price));
                Log.d(TAG, "Set price=" + price + "++++++++++++++++++++");
            }
        } else if (TextUtils.isEmpty(strField) && price == 0) {
                     return;
        } else {
            editText.setText(String.valueOf(price));
        }
    }

    @InverseBindingAdapter(attribute = "textPriceAddEdit", event = "android:textAttrChanged")
    public static double getTextPriceAddEdit(EditText editText) {
        Log.d(TAG, "-----------------------getTextPriceAddEdit: start");
        String strField = editText.getText().toString();
        if (TextUtils.isEmpty(strField)) {
            return 0f;
        }
        BigDecimal result = BigDecimal.valueOf(Double.parseDouble(strField));
        Log.d(TAG, "getTextPriceAddEdit: return=" + result + "-----------------------");
        result = result.setScale(2, RoundingMode.DOWN);
        return result.doubleValue();
    }
}
