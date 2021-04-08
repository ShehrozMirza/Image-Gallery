package com.app.image_gallery.extra

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.google.gson.Gson

fun Activity.displayMetrics(): Pair<Int, Int> {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return Pair(displayMetrics.widthPixels, displayMetrics.heightPixels)
}

fun Int.percent(percentVal: Double): Double {
    return percentVal / 100 * this
}

fun Context.showToast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Any.toJson(): String = Gson().toJson(this)

inline fun <reified T> String.fromJson(): T = Gson().fromJson(this, T::class.java)

fun Activity.setStatusBarColor(@ColorInt color: Int, visibility: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        with(window) {
            addFlags(android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = color
            decorView.systemUiVisibility = visibility //  set status text dark
        }
    }
}

