package maa.coronavirustracker_kotlin_vesion

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView

class Helper {
    fun setCustomColor(
        textView: TextView,
        label: String,
        value: String,
        ColorHex: Int
    ) {
        val LabelValue: String = label + value;
        val spannable: Spannable = SpannableString(LabelValue);
        spannable.setSpan(
            ForegroundColorSpan(ColorHex),
            label.length,
            (label + value).length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannable.setSpan(
            android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
            label.length,
            (label + value).length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        textView.setText(spannable, TextView.BufferType.SPANNABLE);
    }
}